package sa.qiwa.cache.aggregate.ms.infrastructure.repository;

import net.minidev.json.JSONObject;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import reactor.core.publisher.Mono;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateRequest;

public interface AggregateRepository {
    Mono<ReactiveSearchHits<JSONObject>> aggregate(final String entityName, final AggregateRequest aggregateRequest);
}
