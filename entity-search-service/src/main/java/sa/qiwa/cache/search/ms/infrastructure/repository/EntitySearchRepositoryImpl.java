package sa.qiwa.cache.search.ms.infrastructure.repository;

/*import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;*/
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
//import co.elastic.clients.elasticsearch._types.query_dsl.SpanWithinQuery;
import co.elastic.clients.json.JsonData;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
/*import org.springframework.data.elasticsearch.core.query.BaseQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;*/
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EntitySearchRepositoryImpl implements EntitySearchRepository{
    //@Autowired
    ReactiveElasticsearchOperations operations;

    EntitySearchRepositoryImpl(ReactiveElasticsearchOperations operations){
        this.operations = operations;
    }

    @Override
    public Flux<SearchHit<JSONObject>> searchEntity(String entityName, SearchRequest searchCriteria) {


        IndexCoordinates index = IndexCoordinates.of(CommonConstants.entityNameIndexMap.get(entityName));


        return operations.search(buildSearchQuery(searchCriteria), JSONObject.class, index);

    }

    @Override
    public Mono<ReactiveSearchHits<JSONObject>> searchForHits(String entityName, SearchRequest searchCriteria) {
        IndexCoordinates index = IndexCoordinates.of(CommonConstants.entityNameIndexMap.get(entityName));

        Query query = buildSearchQuery(searchCriteria);

        return operations.searchForHits(query, JSONObject.class, index);
    }

    private Query buildSearchQuery(SearchRequest searchCriteria){
       // queryBuilder.withQuery(q -> q.bool(b-> b.must(m-> m.matchAll(ma->ma))));
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withPageable(Pageable.ofSize(searchCriteria.getSize())
                    .withPage(searchCriteria.getOffset()));
        queryBuilder.withRequestCache(true).withTrackTotalHits(true);



        //queryBuilder.withFields(searchCriteria.getFields().stream().map(field->field).collect(Collectors.toList()));
        //queryBuilder.withFields("Salary","EstablishmentNameAr","WorkLocationNameEn");
       /* log.info("--------------------------------------------------------------------");
        searchCriteria.getFields().stream().forEach(field-> log.info(field));
        log.info("--------------------------------------------------------------------");
*/
        /*queryBuilder
                .withQuery(q -> {
                    q.matchAll(ma -> ma);
                    q.match(m->m.field("WorkLocationNameEn").query("Salabekh"));
                    return q;
                });*/
        if(searchCriteria.getFields() != null && searchCriteria.getFields().size()>0){
            queryBuilder.withSourceFilter(new FetchSourceFilter(searchCriteria.getFields().toArray(new String[]{}),new String[]{"meta._id"}));
            //queryBuilder.withFields(searchCriteria.getFields());
            //queryBuilder.withStoredFields(searchCriteria.getFields());
        }

         queryBuilder//NativeQuery.builder()
                //.withQuery(q -> q.matchAll(m->m))
                //.withQuery(q -> q.bool(b->b.must(m->m.match(mt->mt.field("WorkLocationNameEn").query("Salabekh")))))

                //.withQuery(q->q
                        //.match(m->m.field("LaborerName").query("Ibrahim").operator(Operator.Or)))
                .withFilter( q -> q
                        .bool(b -> {
                            searchCriteria.getFilters().stream().forEach(filter -> {
                                switch (filter.getOperator()) {
                                    case EQ:
                                        //b.must(m -> m.term(t -> t.field("LaborerIdNo").value("1043215860")))
                                        b.must(m -> m.term(t -> t
                                                .field(filter.getField())
                                                .value(filter.getValue())));
                                        //.must(m -> m.range(rng -> rng.field("CreationDate").gt(JsonData.of(LocalDate.now().minusYears(1)))));
                                        break;
                                    case GT:
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .gt(JsonData.of(filter.getValue()))));
                                        break;
                                    case LT:
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .lt(JsonData.of(filter.getValue()))));
                                        break;
                                    case GTE:
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .gte(JsonData.of(filter.getValue()))));
                                        break;
                                    case LTE:
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .lte(JsonData.of(filter.getValue()))));
                                        break;
                                    case ANY:
                                        b.should(m -> m.term(t -> t
                                                //.field(filter.getField()).terms(trms->trms.value())
                                                .field(filter.getField())
                                                .value(filter.getValue())));

                                        break;
                                }

                            });
                            return b;
                        }

                        ));

        searchCriteria.getOrders().forEach(order -> {
            queryBuilder.withSort(SortOptionsBuilders.field(builder -> builder.
                    field(order.getField()).
                    order(order.getAsc()?SortOrder.Asc:SortOrder.Desc)));
        });
        return queryBuilder.build();
               /* .withFilter( q -> q
                        .bool(b -> b
                                .must(m -> m.term(t -> t.field("OccupationID").value("911204"))
                                )))*/

        /*searchCriteria.getFilters().stream().forEach(filter -> {

            queryBuilder.withFilter( q -> q.(a -> a
                            a.field(filter.getField())
                            .value(filter.getValue()))


                            );
        });*/
        //queryBuilder.withQuery(q -> q.bool(b-> b.must(m-> m.bool(bool -> bool.filter(filter->filter.match(qb->qb.))))));

       // queryBuilder.withQuery(q -> q.matchAll(ma ->   ma.query(q

           // queryBuilder.withFilter(q-> q.multiMatch(builder -> builder.))
            /*queryBuilder.withQuery(q -> q
                    .match(m -> m
                            .field(filter.getField())
                            .query(filter.getValue())
                    )
            );
        });));


        //queryBuilder.withQuery(q -> q.bool(b-> b.must(m-> m.match(qa->qa.field("SequenceNumber").query(FieldValue.of(18902))))));

        searchCriteria.getFilters().stream().forEach(filter -> {
        /*queryBuilder.withFilter( q -> q.
            bool(b -> b
                .must(m -> m.term(t -> t.
                        field(filter.getField())
                        .value(filter.getValue()))
                )));
           // queryBuilder.withFilter(q-> q.multiMatch(builder -> builder.))
            /*queryBuilder.withQuery(q -> q
                    .match(m -> m
                            .field(filter.getField())
                            .query(filter.getValue())
                    )
            );
        });*/



            //log.info("Search Query == ",queryBuilder.build().getQuery().toString());

        //return operations.search(query, JSONObject.class, index);
    }
}
