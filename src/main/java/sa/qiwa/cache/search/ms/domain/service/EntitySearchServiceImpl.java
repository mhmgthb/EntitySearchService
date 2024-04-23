package sa.qiwa.cache.search.ms.domain.service;

import sa.qiwa.cache.search.ms.domain.model.EntityName;
import sa.qiwa.cache.search.ms.domain.model.Response;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.foundation.enums.ErrorCodes;
import sa.qiwa.cache.search.ms.foundation.exception.ServiceException;
import sa.qiwa.cache.search.ms.infrastructure.repository.EntitySearchRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EntitySearchServiceImpl implements  EntitySearchService {

    @Autowired
    EntitySearchRepository entitySearchRepository;

    @Override
    public Mono<Response> searchEntity(EntityName entityName, SearchRequest searchCriteria) throws ServiceException {
        log.info("----------------EntitySearchServiceImpl -------------------");
        try {
            validateRequest(entityName,searchCriteria);
            Mono<ReactiveSearchHits<JSONObject>> searchForHits = entitySearchRepository.searchForHits(entityName.toString(),searchCriteria);
            Response searchResponse = new Response();

            //searchForHits.flatMap()

           return searchForHits.flatMap(consumer->{
                searchResponse.setTotalRecords(consumer.getTotalHits());
                //searchResponse.setSearchedRecords(
                return consumer.getSearchHits().map(searchHit -> searchHit.getContent()).collectList().
                        onErrorMap(originalException -> {
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
                        }).flatMap(list -> {
                            searchResponse.setRecords(list);
                            return Mono.just(searchResponse);
                        });
                //return Mono.just(searchResponse);

            });
            // return Mono.just(searchResponse);
/*

            searchForHits.map(searchHit->searchHit.getTotalHits()).subscribe(s-> log.info("Total Count: {}",s));

            //searchForHits.log();
            //searchForHits.log().subscribe(s->s.getTotalHits());

           // log.info("Total Search Hits == {}",searchHits.subscribe(s-> s.longValue()));

            Flux<SearchHit<JSONObject>> fluxSearchHits = entitySearchRepository.searchEntity(entityName,searchCriteria);
           //long totalCount = 0;
            fluxSearchHits.count().map(count->count.longValue()).subscribe(aLong -> log.info("Total Count: {}",aLong));


            //log.info("totalCount = {}",totalCount.map(c->c.longValue()).);


            Flux<JSONObject> fluxEntity = fluxSearchHits.map(searchHit -> searchHit.getContent())
                    .onErrorMap(originalException -> {
                        if (originalException instanceof ReadTimeoutException) {
                           throw new ServiceException("....................Readtimeout Excetion occurred......................", ErrorCodes.UNKNOWN);
                        }
                        return originalException;
                    });;

            fluxEntity.count().map(count->count.longValue()).subscribe(aLong -> log.info("Total Count: {}",aLong));;
            //log.info("cnt= {}",cnt.map(c->c.longValue()));

            return fluxEntity.collectList().
                    onErrorMap(originalException -> {
                        if (originalException instanceof ReadTimeoutException) {
                           // log.info("----------------<<<<<<<ReadTimeoutException>>>>>> -------------------");
                            throw new ServiceException("Readtimeout Excetion occurred  ",originalException, ErrorCodes.UNKNOWN);
                        } else if (originalException instanceof DataAccessResourceFailureException) {
                            //log.info("----------------<<<<<<<DataAccessResourceFailureExceptionk>>>>>> -------------------");
                            throw new ServiceException("Elasticsearch connection error.",originalException, ErrorCodes.UNKNOWN);
                        }else if (originalException instanceof UncategorizedElasticsearchException) {
                            //log.info("----------------<<<<<<<UncategorizedElasticsearchException>>>>>> -------------------");
                            throw new ServiceException("Elastic search throws inknown search exception,",originalException, ErrorCodes.BUSINESS_VALIDATION);
                        }
                        return originalException;
                    });*///.log();//.subscribe(employeesList::add);

        } catch (Exception ex) {
            //log.info("----------------<<<<<<<<in catch block>>>>>> -------------------");
            if(ex instanceof ServiceException){
                //log.info("----------------<<<<<<<<ex instanceof ServiceException>>>>>> -------------------");
                throw  ex;
            }
            throw new ServiceException("Error occurred while retrieving data",ex);
        }
    }
    private void validateRequest(EntityName entityName, SearchRequest searchCriteria){
        if(CommonConstants.entityNameIndexMap.get(entityName.getValue()) == null){
            throw new ServiceException(CommonConstants.ENTITY_NOT_IMPLEMNETED,ErrorCodes.NOT_IMPLMENTED);
        }
    }
}
