package com.takamol.search.config;

/*import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.annotation.PostConstruct;*/
import lombok.extern.slf4j.Slf4j;
/*import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;*/
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableReactiveElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Configuration
//@EnableReactiveElasticsearchRepositories( basePackages = "org.springframework.data.elasticsearch.repositories")
public class EntitySearchConfiguration extends ReactiveElasticsearchConfiguration {

    @Value("${api.elastic-search.baseurl}")
    private String serverUrl;

    @Override
    public ClientConfiguration clientConfiguration() {
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("some-header", "on every request")
        httpHeaders.add("Accept", "application/vnd.elasticsearch+json;compatible-with=7");
        httpHeaders.add("Content-Type", "application/vnd.elasticsearch+json;"
                + "compatible-with=7");
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(serverUrl).usingSsl()

                /*.withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))*/
                //.withDefaultHeaders(defaultHeaders)
                .withBasicAuth("elastic", "-r_05kc5qqWqNHZcDWlj")
                .withHeaders(() -> {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("currentTime", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                    return headers;
                }).withClientConfigurer(
                        ElasticsearchClients.ElasticsearchRestClientConfigurationCallback.from(clientBuilder -> {
                            clientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                                @Override
                                public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                                    return httpClientBuilder
                                            .setSSLContext(EntitySearchConfiguration.sslContext("C:/Users/Mohsin Mahmood/keystore.jks","Lenovo@246"))
                                            .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
                                }
                            });
                            return clientBuilder;
                        })).build();
                 // ... other options
        return  clientConfiguration;
    }
    private static SSLContext sslContext(String keystoreFile, String password){
            //throws GeneralSecurityException, IOException {
        try {
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            try (InputStream in = new FileInputStream(keystoreFile)) {
                keystore.load(in, password.toCharArray());
            }
            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keystore, password.toCharArray());

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keystore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(
                    keyManagerFactory.getKeyManagers(),
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());

            return sslContext;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

}
