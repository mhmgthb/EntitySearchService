package com.takamol.qiwa.microservice.search.service;

import com.takamol.qiwa.microservice.search.enums.ErrorCodes;
import com.takamol.qiwa.microservice.search.exception.ServiceException;
import com.takamol.qiwa.microservice.search.model.SearchRequest;
import com.takamol.qiwa.microservice.search.repository.EntitySearchRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.UncategorizedElasticsearchException;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EntitySearchServiceImpl implements  EntitySearchService {

    @Autowired
    EntitySearchRepository entitySearchRepository;

    @Override
    public Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria) throws ServiceException {
        //throw  new EntitySearchException("Readtimeout Excetion occurred", Error.SERVER_ERROR);
        try {
            Flux<SearchHit<JSONObject>> fluxSearchHits = entitySearchRepository.searchEntity(searchCriteria);
            log.info("----------------EntitySearchServiceImpl -------------------1");
            fluxSearchHits.onErrorMap(originalException -> {
                if (originalException instanceof ReadTimeoutException) {
                    new ServiceException("....................Readtimeout Excetion occurred......................", ErrorCodes.UNKNOWN);
                }/*else if(originalException instanceof DataAccessResourceFailureException){
                log.info("----------------DataAccessResourceFailureException -------------------");
                new EntitySearchException("Readtimeout Excetion occurred", Error.SERVER_ERROR);
            }*/
                return originalException;
            });
            //log.info("----------------EntitySearchServiceImpl -------------------2");
            Flux<JSONObject> fluxEntity = fluxSearchHits.map(searchHit -> searchHit.getContent());
            //List<JSONObject> employeesList = new ArrayList<>();
            //log.info("----------------<<<<<<<<EntitySearchServiceImpl>>>>>> -------------------3");
            return fluxEntity.collectList().
                    onErrorMap(originalException -> {
                        if (originalException instanceof ReadTimeoutException) {
                           // log.info("----------------<<<<<<<ReadTimeoutException>>>>>> -------------------");
                            throw new ServiceException("Readtimeout Excetion occurred  ",originalException, ErrorCodes.UNKNOWN);
                        } else if (originalException instanceof DataAccessResourceFailureException) {
                            //log.info("----------------<<<<<<<DataAccessResourceFailureExceptionk>>>>>> -------------------");
                            throw new ServiceException("DataAccessResourceFailureException Excetion occurred",originalException, ErrorCodes.UNKNOWN);
                        }else if (originalException instanceof UncategorizedElasticsearchException) {
                            //log.info("----------------<<<<<<<UncategorizedElasticsearchException>>>>>> -------------------");
                            throw new ServiceException("Elastic search throws inknown searching exception,",originalException, ErrorCodes.BUSINESS_VALIDATION);
                        }
                        return originalException;
                    });//.log();//.subscribe(employeesList::add);

        } catch (Exception ex) {
            //log.info("----------------<<<<<<<<in catch block>>>>>> -------------------");
            if(ex instanceof ServiceException){
                //log.info("----------------<<<<<<<<ex instanceof ServiceException>>>>>> -------------------");
                throw  ex;
            }
            throw new ServiceException("Error occurred while retrieving data",ex);
        }
    }
}
