package com.takamol.qiwa.microservice.search.config;


import com.takamol.qiwa.microservice.search.common.util.CommonUtility;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.support.HttpHeaders;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
public class EntitySearchConfiguration extends ReactiveElasticsearchConfiguration {

    @Value("${api.elastic-search.baseurl}")
    private String serverUrl;
    @Value("${elasticsearch.username}")
    private String esUsername;
    @Value("${elasticsearch.pwd}")
    private String esPwd;
    @Value("${keystore.file.path}")
    private String keystoreFilePath;;
    @Value("${keystore.pwd}")
    private String keystorePwd;

    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders httpHeaders = new HttpHeaders();
        log.info("esid =={}",esUsername);
        httpHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        httpHeaders.add("Content-Type", "application/vnd.elasticsearch+json;"
                + "compatible-with=7");
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(serverUrl).usingSsl()
                /*.withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))*/
                //.withDefaultHeaders(defaultHeaders)
                .withBasicAuth(esUsername, esPwd)
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return headers;
                }).withClientConfigurer(
                        ElasticsearchClients.ElasticsearchRestClientConfigurationCallback.from(clientBuilder -> {
                            clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback(){
                                @Override
                                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {

                                    try {
                                        return httpClientBuilder
                                                .setSSLContext(CommonUtility.sslContext(keystoreFilePath,keystorePwd))
                                                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                                    } catch (Exception e) {
                                        log.error("An error occurred while reading certificate from jks file. and initializing SSL context for HttpClient",e);
                                    }
                                    return null;
                                }
                            });
                            return clientBuilder;
                        })).build();

        return  clientConfiguration;
    }

}
