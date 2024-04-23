package sa.qiwa.cache.search.ms.application.delegator;

import sa.qiwa.cache.search.ms.application.controller.ApiUtil;
import sa.qiwa.cache.search.ms.application.controller.SearchApiController;
import sa.qiwa.cache.search.ms.domain.model.EntityName;
import sa.qiwa.cache.search.ms.domain.model.Response;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import sa.qiwa.cache.search.ms.presentation.api.SearchApi;

import java.util.Optional;
import jakarta.annotation.Generated;

/**
 * A delegate to be called by the {@link SearchApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface SearchApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /search/{entityName} : Search entity using elastic search service
     * Search entity using search service
     *
     * @param entityName  (required)
     * @param searchRequest search Criteria will contain all filters and other criteria to search entity (required)
     * @return Success (status code 200)
     *         or Bad request (status code 400)
     *         or Entity not found (status code 404)
     *         or Validation error (status code 409)
     *         or Internal server error (status code 500)
     *         or The request method is not supported (Not Implemented) (status code 501)
     *         or Service unavailable due to invalid backend response(Bad Gateway) (status code 502)
     *         or Service unavailable (Under maintenance or overloaded) (status code 503)
     * @see SearchApi#searchEnity
     */
    default Mono<ResponseEntity<Response>> searchEnity(EntityName entityName,
        SearchRequest searchRequest,
        ServerWebExchange exchange) {
        Mono<Void> result = Mono.empty();
        exchange.getResponse().setStatusCode(HttpStatus.NOT_IMPLEMENTED);
        for (MediaType mediaType : exchange.getRequest().getHeaders().getAccept()) {
            if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                String exampleString = "{ \"totalRecords\" : 0, \"records\" : [ \"\", \"\" ] }";
                result = ApiUtil.getExampleResponse(exchange, mediaType, exampleString);
                break;
            }
        }
        return result.then(Mono.empty());

    }

}
