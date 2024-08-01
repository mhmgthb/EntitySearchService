package sa.qiwa.cache.aggregate.ms.application.delegator;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import sa.qiwa.cache.aggregate.ms.application.controller.ApiUtil;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateRequest;

import jakarta.annotation.Generated;
import sa.qiwa.cache.aggregate.ms.domain.model.EntityName;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateResponse;

import java.util.Optional;

/**
 * A delegate to be called by the {@link AggregateApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface AggregateApiDelegate {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /aggregate/{entityName} : Aggregation of searched results
     * This service will aggregate searched results
     *
     * @param entityName  (required)
     * @param aggregateRequest Will contain required aggregation operators and corresponding fields (required)
     * @return Success (status code 200)
     *         or Bad request (status code 400)
     *         or Entity not found (status code 404)
     *         or Validation error (status code 409)
     *         or Internal server error (status code 500)
     *         or The request method is not supported (Not Implemented) (status code 501)
     *         or Service unavailable due to invalid backend response(Bad Gateway) (status code 502)
     *         or Service unavailable (Under maintenance or overloaded) (status code 503)
     * @see AggregateApi#aggregate
     */
    default Mono<ResponseEntity<AggregateResponse>> aggregate(EntityName entityName,
                                                              AggregateRequest aggregateRequest,
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
