package sa.qiwa.cache.search.ms.foundation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
//import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
//import io.netty.handler.timeout.ReadTimeoutHandler;
//import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

//@Configuration
//@EnableWebFlux
public class EntitySearchConfigurationOld implements WebFluxConfigurer{
    Logger logger = LoggerFactory.getLogger(EntitySearchConfigurationOld.class);

   // @Value("${api.host.baseurl}")
    private String apiHost;

    //@Bean
    public WebClient getWebClient()
    {
/*        HttpClient httpClient = HttpClient.create()
                .tcpConfiguration(client ->
                        client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10))
                                        .addHandlerLast(new WriteTimeoutHandler(10))));*/

        HttpClient httpClient = HttpClient.create().option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000);//.doOnConnected(conn -> conn
               // .addHandlerLast(new ReadTimeoutHandler(10))
                //.addHandlerLast(new ResponseTimeoutHandler(10))
               // .addHandlerLast(new WriteTimeoutHandler(10)));

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient.wiretap(true));

        return WebClient.builder()
                .baseUrl("https://192.168.80.41:9200")
                .clientConnector(connector)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        /*WebClient webClient = WebClient.builder()
                .baseUrl(apiHost)
                //.defaultCookie("cookie-name", "cookie-value")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();*/
    }

    public static void main(String[] args) {
        EntitySearchConfigurationOld entitySearchConfiguration = new EntitySearchConfigurationOld();
        Object object  = entitySearchConfiguration.getWebClient().get()
                .uri("/")
                .retrieve()
                .bodyToMono(Object.class).doOnError(error ->  new Exception("asdasasd"));
                //.onErrorMap(Exception.class, ex -> new RuntimeException("Another Exception"));

        System.out.println(" System.getProperty(user.home) "+ System.getProperty("user.home"));
    }
}
