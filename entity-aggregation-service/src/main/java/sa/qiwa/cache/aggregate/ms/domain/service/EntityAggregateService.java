package sa.qiwa.cache.aggregate.ms.domain.service;

import reactor.core.publisher.Mono;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateRequest;
import sa.qiwa.cache.aggregate.ms.domain.model.EntityName;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateResponse;

public interface EntityAggregateService {
     public  Mono<AggregateResponse> searchEntity(EntityName entityName, AggregateRequest aggregateRequest);

}
