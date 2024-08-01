package sa.qiwa.cache.aggregate.ms.application.delegator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sa.qiwa.cache.aggregate.ms.domain.service.EntityAggregateService;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateRequest;
import sa.qiwa.cache.aggregate.ms.domain.model.EntityName;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateResponse;

@Component
@Slf4j
public class EntitySearchWithAggregationDelegatorImpl implements AggregateApiDelegate {

    @Autowired
    private EntityAggregateService entitySearchService;

/*    @Override
    public Mono<ResponseEntity<SearchResponse>> searchEnity(EntityName entityName,
                                                            SearchRequest searchRequest,
                                                            ServerWebExchange exchange) {
        //log.info(" {} for {} search with Payload;{}",this.getClass().getSimpleName(),entityName, JsonUtil.convertObjectToString(searchRequest));

        return entitySearchService.searchEntity(entityName,searchRequest).map(response -> new ResponseEntity<>(response,HttpStatus.OK));

    }*/

    @Override
    public Mono<ResponseEntity<AggregateResponse>> aggregate(EntityName entityName, AggregateRequest aggregateRequest, ServerWebExchange exchange) {
        log.info("Request in AggregateApiDelegate");
        return entitySearchService.searchEntity(entityName,aggregateRequest).map(response -> new ResponseEntity<>(response,HttpStatus.OK));

    }
}
