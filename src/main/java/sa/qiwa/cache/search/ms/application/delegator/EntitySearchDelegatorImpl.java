package sa.qiwa.cache.search.ms.application.delegator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import sa.qiwa.cache.search.ms.domain.model.EntityName;
import sa.qiwa.cache.search.ms.domain.model.Response;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import sa.qiwa.cache.search.ms.domain.service.EntitySearchService;

//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class EntitySearchDelegatorImpl implements SearchApiDelegate {

    @Autowired
    private EntitySearchService entitySearchService;

    @Override
    public Mono<ResponseEntity<Response>> searchEnity(EntityName entityName,
                                                      SearchRequest searchRequest,
                                                      ServerWebExchange exchange) {
        log.info("Request received in {} for {} search",this.getClass().getSimpleName(),entityName);

        return entitySearchService.searchEntity(entityName,searchRequest).map(response -> new ResponseEntity<>(response,HttpStatus.OK));

    }

}
