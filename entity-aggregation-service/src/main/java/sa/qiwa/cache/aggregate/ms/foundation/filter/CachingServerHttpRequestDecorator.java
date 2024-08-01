package sa.qiwa.cache.aggregate.ms.foundation.filter;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import reactor.core.publisher.Flux;

import java.time.OffsetDateTime;

import static io.netty.util.CharsetUtil.UTF_8;

@Slf4j
public class CachingServerHttpRequestDecorator extends ServerHttpRequestDecorator {

    @Getter
    private final OffsetDateTime timestamp = OffsetDateTime.now();
    private final StringBuilder cachedBody = new StringBuilder();

    CachingServerHttpRequestDecorator(ServerHttpRequest delegate) {
        super(delegate);
    }

    @Override
    public Flux<DataBuffer> getBody() {
        return super.getBody().doOnNext(this::cache);
    }

    @SneakyThrows
    private void cache(DataBuffer buffer) {
        cachedBody.append(UTF_8.decode(buffer.asByteBuffer()).toString());
        log.info("{}", cachedBody);
    }

    public String getCachedBody() {
        return cachedBody.toString();
    }
}
