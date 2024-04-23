package sa.qiwa.cache.search.ms.domain.service;

import sa.qiwa.cache.search.ms.domain.model.EntityName;
import sa.qiwa.cache.search.ms.domain.model.Response;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import sa.qiwa.cache.search.ms.foundation.exception.ServiceException;
//import org.json.JSONObject;
import reactor.core.publisher.Mono;

public interface EntitySearchService {
     public  Mono<Response> searchEntity(EntityName entityName, SearchRequest searchCriteria) throws ServiceException;

}
