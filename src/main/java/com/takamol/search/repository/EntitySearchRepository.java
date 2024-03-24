package com.takamol.search.repository;

import com.takamol.search.model.SearchRequest;
import net.minidev.json.JSONObject;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

public interface EntitySearchRepository {
    public Flux<SearchHit<JSONObject>> searchEntity(SearchRequest searchCriteria);
}
