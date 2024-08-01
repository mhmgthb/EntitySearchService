package sa.qiwa.cache.search.ms.foundation.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebExchangeDecorator;

public final class PartnerServerWebExchangeDecorator extends ServerWebExchangeDecorator {

    private final ServerHttpRequestDecorator requestDecorator;
    private final ServerHttpResponseDecorator responseDecorator;

    public PartnerServerWebExchangeDecorator(ServerWebExchange delegate) {
        super(delegate);
        this.requestDecorator = new CachingServerHttpRequestDecorator(delegate.getRequest());
        this.responseDecorator = new ServerHttpResponseDecorator(delegate.getResponse());
    }

    @Override
    public ServerHttpRequest getRequest() {
        return requestDecorator;
    }

    @Override
    public ServerHttpResponse getResponse() {
        return responseDecorator;
    }

}
