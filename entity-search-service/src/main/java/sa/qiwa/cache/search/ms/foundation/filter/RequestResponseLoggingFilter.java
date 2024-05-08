package sa.qiwa.cache.search.ms.foundation.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractor;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.foundation.util.LoggingUtil;

import java.nio.CharBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestResponseLoggingFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        String requestId = LoggingUtil.getCorrelationId(request.getHeaders());
        response.getHeaders().add(CommonConstants.X_REQUEST_ID,requestId);
        long start = System.currentTimeMillis();

        /*try (MDC.MDCCloseable cMdc = MDC.putCloseable(CommonConstants.MDC_KEY, requestId)) {
            //logStatement.accept(signal.get());
            log.info("{} {}", request.getMethod(), request.getURI());
            request.getBody().map(
                    dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        return bytes;
                    }
            );
        }*/
        return chain
                .filter(exchange)
                .doFirst(()->{
                    try (MDC.MDCCloseable cMdc = MDC.putCloseable(CommonConstants.MDC_KEY, requestId)) {
                        //logStatement.accept(signal.get());
                        log.info("{} {}", request.getMethod(), request.getURI());
                        request.getBody().map(
                                dataBuffer -> {
                                    log.info("Reading request body..............");
                                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                    dataBuffer.read(bytes);
                                    DataBufferUtils.release(dataBuffer);
                                    log.info("Request Payload: {}",String.valueOf(bytes));
                                    return bytes;
                                }
                        );//.collect(
                            //    bytes->log.info("Request Payload: {}",String.valueOf(bytes)));
                    }
                   // log.info("{} {}", request.getMethod(), request.getURI());
                })
                /*.doOnEach(
                       // logOnEach(r -> log.info("{} {}", request.getMethod(), request.getURI()))
                     signal -> {

                        String contextValue = signal.getContextView().get(CommonConstants.CONTEXT_KEY);
                        try (MDC.MDCCloseable cMdc = MDC.putCloseable(CommonConstants.MDC_KEY, contextValue)) {
                            //logStatement.accept(signal.get());
                            log.info("{} {}", request.getMethod(), request.getURI());
                           *//*request.getBody().map(
                                    dataBuffer -> {
                                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(bytes);
                                        DataBufferUtils.release(dataBuffer);
                                        return bytes;
                                    }
                            );*//*

                        }
                    })*/
                .contextWrite(Context.of(CommonConstants.CONTEXT_KEY, requestId))
                .doFinally((r) -> {
                    try (MDC.MDCCloseable cMdc = MDC.putCloseable(CommonConstants.MDC_KEY, requestId)) {
                        //logStatement.accept(signal.get());
                        log.info("{} {}", request.getMethod(), request.getURI());
                    }

                    MDC.clear();
                });
    }
    private String getCorrelationId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get(CommonConstants.X_REQUEST_ID);
        return requestIdHeaders == null || requestIdHeaders.isEmpty()
                ? UUID.randomUUID().toString()
                : requestIdHeaders.get(0);
    }
}
