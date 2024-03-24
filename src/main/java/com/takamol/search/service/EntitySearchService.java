package com.takamol.search.service;

import com.takamol.search.model.SearchRequest;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.List;

public interface EntitySearchService {
     public  Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria);

}
