package sa.qiwa.cache.aggregate.ms.domain.service;

import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.aggregations.FilterAggregate;
import co.elastic.clients.elasticsearch._types.aggregations.StatsAggregate;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import sa.qiwa.cache.aggregate.ms.domain.model.*;
import sa.qiwa.cache.aggregate.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.aggregate.ms.foundation.enums.ErrorCodes;
import sa.qiwa.cache.aggregate.ms.foundation.exception.ServiceException;


import sa.qiwa.cache.aggregate.ms.infrastructure.repository.AggregateRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EntityAggregateServiceImpl implements EntityAggregateService {


    AggregateRepository agregateRepository;

    EntityAggregateServiceImpl(AggregateRepository agregateRepository){
        this.agregateRepository = agregateRepository;
    }
    @Override
    public Mono<AggregateResponse> searchEntity(EntityName entityName, AggregateRequest aggregateRequest) throws ServiceException {

        try {
           // log.info("Validation started...........");
            //validateRequest(entityName,aggregateRequest);
           // log.info("Validation done...........");
            Mono<ReactiveSearchHits<JSONObject>> searchForHits = agregateRepository.aggregate(entityName.toString(),aggregateRequest);
            AggregateResponse aggregateResponse = new AggregateResponse();

           return searchForHits
                   //.doFirst(()->log.info("Searching started for {}",entityName.toString()))
                   .flatMap(searchHits->{
                        log.info(".................processing searchforhits...................");

                        //searchResponse.setSearchedRecords(
                       if(searchHits.hasAggregations()){
                          /*double aggregation = ((ElasticsearchAggregations) searchHits
                                   .getAggregations())
                                   .get("agg_stats")
                                   .aggregation().getAggregate().min().value();*/

                                   //.getAggregate().valueCount();

                                  /* .array()
                                   .stream()
                                   .collect(Collectors.toMap(StringTermsBucket::key, StringTermsBucket::docCount));*/
                           //log.info("aggregation: {} ",aggregation);
                           FilterAggregate  filteAaggregate = ((ElasticsearchAggregations) searchHits
                                   .getAggregations())
                                   .get(CommonConstants.AGGREGATE_METRIC)
                                   .aggregation()
                                   .getAggregate().filter();

                           aggregateResponse.docCount(BigDecimal.valueOf(filteAaggregate.docCount()));
                           /*double sum = aggregate.aggregations()
                                   .get(aggregateRequest.getAggregation().getValue()).sum().value();*/

                           Aggregate nestedAggregate = filteAaggregate.aggregations().get(CommonConstants.NESTED_AGGREGATE);
                            String aggName = aggregateRequest.getAggregation().getValue();
                           Aggregate aggregate = nestedAggregate != null && nestedAggregate.isNested() ? nestedAggregate.nested().aggregations().get(aggName) : filteAaggregate.aggregations()
                                   .get(aggName);
                           double aggValue = 0;
                           StatsAggregate statsAggregate = null;
                           switch (aggregateRequest.getAggregation()) {
                               case COUNT -> aggValue = aggregate.sum().value();
                               case AVG ->  aggValue = aggregate.avg().value();
                               case MAX ->  aggValue = aggregate.max().value();
                               case MIN -> aggValue = aggregate.min().value();
                               case STATS -> statsAggregate = aggregate.stats();
                           }
                           if(aggregateRequest.getAggregation() == AggregateRequest.AggregationEnum.STATS){
                               //List.of(AggregateRequest.AggregationEnum.COUNT,AggregateRequest.AggregationEnum.STATS);
                               if(statsAggregate!=null) {
                                   aggregateResponse.setAggregations(List.of(
                                           makeAggregationObject(statsAggregate,AggregateRequest.AggregationEnum.COUNT),
                                           makeAggregationObject(statsAggregate,AggregateRequest.AggregationEnum.AVG),
                                           makeAggregationObject(statsAggregate,AggregateRequest.AggregationEnum.MAX),
                                           makeAggregationObject(statsAggregate,AggregateRequest.AggregationEnum.MIN)
                                   ));
                               }

                           }else{
                               Aggregation aggregation = new Aggregation();
                               aggregation.setMetric(aggregateRequest.getAggregation().getValue());
                               aggregation.setMetricValue(aggValue);
                               aggregateResponse.setAggregations(List.of(aggregation));
                           }


                           //return  Mono.just(Map.of(aggregateRequest.getAggregation().getValue(),aggregation));
                                  /* .lterms()
                                   .buckets()
                                   .array()
                                   .stream()
                                   .collect(Collectors.toMap(LongTermsBucket::key
                                           , LongTermsBucket::));
                           log.info("aggregation: {} ",aggregation);*/
                       }

                return Mono.just(aggregateResponse);

            }).onErrorMap(this::mapException);

        }/*catch(UncategorizedElasticsearchException uee) {

        }*/catch (Exception ex) {
            //log.info("----------------<<<<<<<<in catch block>>>>>> -------------------");
            if(ex instanceof ServiceException){
                //log.info("----------------<<<<<<<<ex instanceof ServiceException>>>>>> -------------------");
                throw  ex;
            }
            throw new ServiceException(CommonConstants.INTERNAL_SERVER_ERROR,ex);
        }
    }
    private Throwable mapException(Throwable originalException){
        if (originalException instanceof ReadTimeoutException) {
            log.error("Readtimeout Excetion occurred ");
            throw new ServiceException(CommonConstants.INTERNAL_SERVER_ERROR,originalException, ErrorCodes.UNKNOWN);
        } else if (originalException instanceof DataAccessResourceFailureException) {
            log.error("Elasticsearch connection error.");
            throw new ServiceException(CommonConstants.INTERNAL_SERVER_ERROR,originalException, ErrorCodes.UNKNOWN);
        }else if (originalException instanceof UncategorizedElasticsearchException) {
            log.error("Elastic search throws inknown search exception.");
            throw new ServiceException(CommonConstants.ELASTICSEARCH_QUERY_EXCEPTION,originalException, ErrorCodes.BUSINESS_VALIDATION);
        }
        return originalException;
    }
    private void validateRequest(EntityName entityName, SearchRequest searchRequest){
        if(CommonConstants.entityNameIndexMap.get(entityName.getValue()) == null){
            throw new ServiceException(CommonConstants.ENTITY_NOT_IMPLEMNETED,ErrorCodes.NOT_IMPLMENTED);
        }
        searchRequest.getFilters().forEach(filter -> {
            if(filter.getOperator() == Filter.OperatorEnum.BETWEEN || filter.getOperator() == Filter.OperatorEnum.NOT_BETWEEN){
                if(!filter.getField().contains(" "))
                    throw new ServiceException(CommonConstants.BAD_REQUEST,ErrorCodes.BAD_REQUEST);
            }
        });
    }
    private Aggregation makeAggregationObject(StatsAggregate statsAggregate, AggregateRequest.AggregationEnum aggregationEnum){
        Aggregation aggregation = new Aggregation();
        aggregation.setMetric(aggregationEnum.getValue());
        switch (aggregationEnum){
            case COUNT -> aggregation.setMetricValue(statsAggregate.sum());
            case AVG -> aggregation.setMetricValue(statsAggregate.avg());
            case MAX -> aggregation.setMetricValue(statsAggregate.max());
            case MIN -> aggregation.setMetricValue(statsAggregate.min());

        }
        return aggregation;
    }
}
