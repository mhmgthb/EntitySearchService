package com.takamol.qiwa.microservice.search.service;

import com.takamol.qiwa.microservice.search.exception.ServiceException;
import com.takamol.qiwa.microservice.search.model.SearchRequest;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EntitySearchService {
     public  Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria) throws ServiceException;

}
