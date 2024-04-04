package com.takamol.qiwa.microservice.search.repository;

import com.takamol.qiwa.microservice.search.model.SearchRequest;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

public interface EntitySearchRepository {
    public Flux<SearchHit<JSONObject>> searchEntity(SearchRequest searchCriteria);
}
