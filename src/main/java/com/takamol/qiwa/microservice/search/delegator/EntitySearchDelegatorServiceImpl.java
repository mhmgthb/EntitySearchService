package com.takamol.qiwa.microservice.search.delegator;

import com.takamol.qiwa.microservice.search.model.SearchRequest;
import com.takamol.qiwa.microservice.search.service.EntitySearchService;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
@Component
public class EntitySearchDelegatorServiceImpl implements EntitySearchDelegatorService {

    @Autowired
    private EntitySearchService entitySearchService;

    @Override
    public Mono<List<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria)  {
        return entitySearchService.searchEntity(entityName,searchCriteria);
    }
}
