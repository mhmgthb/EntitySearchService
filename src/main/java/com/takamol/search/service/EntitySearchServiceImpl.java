package com.takamol.search.service;

import com.takamol.search.enums.Error;
import com.takamol.search.exception.EntitySearchException;
import com.takamol.search.model.SearchRequest;
import com.takamol.search.repository.EntitySearchRepository;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EntitySearchServiceImpl implements  EntitySearchService{

    @Autowired
    EntitySearchRepository entitySearchRepository;

    @Override
    public Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria) {
       //throw  new EntitySearchException("Readtimeout Excetion occurred", Error.SERVER_ERROR);

        Flux<SearchHit<JSONObject>> fluxSearchHits = entitySearchRepository.searchEntity(searchCriteria);
        log.info("----------------EntitySearchServiceImpl -------------------1");
        fluxSearchHits.onErrorMap(originalException -> {
            if(originalException instanceof ReadTimeoutException){
                new EntitySearchException("....................Readtimeout Excetion occurred......................", Error.SERVER_ERROR);
            }/*else if(originalException instanceof DataAccessResourceFailureException){
                log.info("----------------DataAccessResourceFailureException -------------------");
                new EntitySearchException("Readtimeout Excetion occurred", Error.SERVER_ERROR);
            }*/
            return originalException;
        });
        log.info("----------------EntitySearchServiceImpl -------------------2");
        Flux<JSONObject> fluxEntity = fluxSearchHits.map(searchHit -> searchHit.getContent());
        List<JSONObject> employeesList = new ArrayList<>();
        log.info("----------------<<<<<<<<EntitySearchServiceImpl>>>>>> -------------------3");
        return fluxEntity.collectList().
                onErrorMap(originalException -> {
                    if(originalException instanceof ReadTimeoutException){
                         new EntitySearchException("Readtimeout Excetion occurred  ", Error.SERVER_ERROR);
                    }else if(originalException instanceof DataAccessResourceFailureException){
                        originalException.printStackTrace();
                         new EntitySearchException("DataAccessResourceFailureException Excetion occurred", Error.SERVER_ERROR);
                    }
                    return originalException;
                }).log();//.subscribe(employeesList::add);
       // log.info("----------------EntitySearchServiceImpl -------------------4");
       // return employeesList;

    }
}
