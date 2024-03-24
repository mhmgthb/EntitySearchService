package com.takamol.search.repository;

import com.takamol.search.model.SearchRequest;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EntityRepositoryImpl implements EntitySearchRepository{
    @Autowired
    ReactiveElasticsearchOperations operations;



    @Override
    public Flux<SearchHit<JSONObject>> searchEntity(SearchRequest searchCriteria) {

        IndexCoordinates index = IndexCoordinates.of("qiwa-contracts-v2");
        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .matchAll(ma -> ma))
                .withFilter( q -> q
                        .bool(b -> b
                                .must(m -> m
                                        .term(t -> t
                                                .field("ContractPeriod")
                                                .value("12"))
                                ))).build();


        return operations.search(query, JSONObject.class, index);
    }
}
