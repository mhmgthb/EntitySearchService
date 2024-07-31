package sa.qiwa.cache.search.ms.domain.service;

import sa.qiwa.cache.search.ms.domain.model.EntityName;
import sa.qiwa.cache.search.ms.domain.model.Filter;
import sa.qiwa.cache.search.ms.domain.model.SearchResponse;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.foundation.enums.ErrorCodes;
import sa.qiwa.cache.search.ms.foundation.exception.ServiceException;
import sa.qiwa.cache.search.ms.infrastructure.repository.EntitySearchRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EntitySearchServiceImpl implements  EntitySearchService {

    //@Autowired
    EntitySearchRepository entitySearchRepository;

    EntitySearchServiceImpl(EntitySearchRepository entitySearchRepository){
        this.entitySearchRepository = entitySearchRepository;
    }
    @Override
    public Mono<SearchResponse> searchEntity(EntityName entityName, SearchRequest searchCriteria) throws ServiceException {

        try {
            log.info("Validation started...........");
            validateRequest(entityName,searchCriteria);
            log.info("Validation done...........");
            Mono<ReactiveSearchHits<JSONObject>> searchForHits = entitySearchRepository.searchForHits(entityName.toString(),searchCriteria);
            SearchResponse searchResponse = new SearchResponse();

           return searchForHits
                   //.doFirst(()->log.info("Searching started for {}",entityName.toString()))
                   .flatMap(consumer->{
                        searchResponse.setTotalRecords(consumer.getTotalHits());
                        //searchResponse.setSearchedRecords(
                        return consumer.getSearchHits().map(searchHit -> searchHit.getContent())
                                                        .collectList()
                                                        .onErrorMap(this::mapException).flatMap(list -> {
                                                                searchResponse.setRecords(list);
                                                                return Mono.just(searchResponse);
                                });
                //return Mono.just(searchResponse);

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
                if(!filter.getField().contains(" ")){
                    log.error("Upper and lower limits must be separated with spaces");
                    throw new ServiceException(CommonConstants.BETWEEN_VALUE_INVALID,ErrorCodes.BAD_REQUEST);
                }
            }
        });
    }
}
