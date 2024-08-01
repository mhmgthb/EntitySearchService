package sa.qiwa.cache.search.ms.test;


import co.elastic.clients.elasticsearch.core.DeleteByQueryRequest;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import sa.qiwa.cache.search.ms.domain.entity.Establishment;
import sa.qiwa.cache.search.ms.domain.model.SearchResponse;
import sa.qiwa.cache.search.ms.infrastructure.repository.TestSearchRepository;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
//@SpringBootTest
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@SpringJUnitConfig(EntitySearchServiceApplication.class)
//@PropertySource("classpath:application.yaml")
//@TestPropertySource("classpath:application.yaml")
@ActiveProfiles("demo")
public class EntitySearchServiceApplicationTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final  String url = "/search";
    private static final  String entityName = "TestEst";

    @BeforeAll
    static void  setUp(ApplicationContext context ) {
        //client = WebTestClient.bindToApplicationContext(context).build();
        //System.out.printf("Application  Name: "+context.getApplicationName());
        //operations.putScript(Script.builder().build());
        log.info("in before each ");
        ReactiveElasticsearchOperations operations = context.getBean(ReactiveElasticsearchOperations.class);

        boolean exist = operations.indexOps(Establishment.class).exists().block().booleanValue();

            if(exist){
                log.info("Deleting index..........");
                if(operations.indexOps(Establishment.class).delete().block().booleanValue()){
                    log.info("Creating index..........");
                    if(operations.indexOps(Establishment.class).create().block().booleanValue()){
                        log.info("Adding data.........");
                        TestSearchRepository repository = context.getBean(TestSearchRepository.class);
                        repository.saveAll(TestConfig.getEstablishmentList());
                    }
                }
            }

               /* NativeQuery deleteQuery = NativeQuery.builder().withQuery(
                        builder -> builder.matchAll(builder1 -> builder1)).build();
                operations.delete(deleteQuery).map(s -> {
                    log.info("deleted string....{}",s);
                    TestSearchRepository repository = context.getBean(TestSearchRepository.class);
                    repository.saveAll(TestConfig.getEstablishmentList());
                    return s;
                }).block();*/




    }

    @Test
    void test_when_entity_name_is_not_given() throws Exception {

        webTestClient.post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
                //.isOk()
                //.expectBody(SearchResponse.class)
                //.consumeWith(serverResponse ->
                //assertNotNull(serverResponse.getResponseBody()));
    }
    /*@Test
    public void test_when_request_does_not_conforms_to_schema() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getInvalidSchemaRequest()))
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_some_filter_has_empty_field() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getRequestWithEmptyFilterField()))
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_page_size_is_invalid() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getRequestWithInvalidPageSize()))

                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_offset_size_is_invalid() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getRequestWithInvalidOffsetSize()))

                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_order_field_is_null() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getRequestWithOrderFieldNulle()))
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_order_field_is_invalid() {
        webTestClient.post()
                .uri(url,entityName)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getRequestWithOrderFieldInvalid()))
                .exchange()
                .expectStatus().isBadRequest();
    }
    @Test
    public void test_when_request_is_valid() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk();
    }
    @Test
    public void test_response_list_size_as_per_page_size() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->
                assertEquals(serverResponse.getResponseBody().getRecords().size() , TestConfig.getSearchRequest().getSize()));
    }
    @Test
    public void test_response_list_size_as_per_equal_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->
                        assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2));
    }
    @Test
    public void test_total_records_as_per_not_equal_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                        assertNotNull(serverResponse.getResponseBody());
                        assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_any_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_lt_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_lte_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_gt_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_gte_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_between_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }
    @Test
    public void test_total_records_as_per_not_between_filters() {
        webTestClient.post()
                .uri(url,entityName)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(TestConfig.getSearchRequest()))
                .exchange().expectStatus().isOk()
                .expectBody(SearchResponse.class)
                .consumeWith(serverResponse ->{
                    assertNotNull(serverResponse.getResponseBody());
                    assertEquals(serverResponse.getResponseBody().getTotalRecords() , 2);
                });
    }*/
}
