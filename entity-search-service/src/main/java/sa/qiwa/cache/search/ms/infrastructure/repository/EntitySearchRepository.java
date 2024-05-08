package sa.qiwa.cache.search.ms.infrastructure.repository;

import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EntitySearchRepository {
    public Flux<SearchHit<JSONObject>> searchEntity(String entityName,SearchRequest searchCriteria);
    public Mono<ReactiveSearchHits<JSONObject>> searchForHits(String entityName, SearchRequest searchCriteria);

}
