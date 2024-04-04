package com.takamol.qiwa.microservice.search.delegator;

import com.takamol.qiwa.microservice.search.model.SearchRequest;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.List;


public interface EntitySearchDelegatorService {
    public Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria);

}
