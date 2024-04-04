package com.takamol.qiwa.microservice.search.repository;

/*import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;*/
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
//import co.elastic.clients.elasticsearch._types.query_dsl.SpanWithinQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import co.elastic.clients.json.JsonData;
import com.takamol.qiwa.microservice.search.model.Filter;
import com.takamol.qiwa.microservice.search.model.SearchRequest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
/*import org.springframework.data.elasticsearch.core.query.BaseQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;*/
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@Component
public class EntitySearchRepositoryImpl implements EntitySearchRepository{
    @Autowired
    ReactiveElasticsearchOperations operations;

    @Override
    public Flux<SearchHit<JSONObject>> searchEntity(SearchRequest searchCriteria) {

        IndexCoordinates index = IndexCoordinates.of("qiwa-contracts-v2");
        NativeQueryBuilder queryBuilder = NativeQuery.builder();


       // queryBuilder.withQuery(q -> q.bool(b-> b.must(m-> m.matchAll(ma->ma))));

        queryBuilder.withPageable(Pageable.ofSize(searchCriteria.getSize()).withPage(searchCriteria.getOffset()));


        Query query = NativeQuery.builder()
                .withQuery(q -> q
                        .matchAll(ma -> ma))
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
                                }

                            });
                            return b;
                        }

                        )).build();
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

        /*searchCriteria.getOrders().forEach(order -> {
            queryBuilder.withSort(SortOptionsBuilders.field(builder -> builder.
                    field(order.getField()).
                    order(order.isAsc()?SortOrder.Asc:SortOrder.Desc)));
        });*/

            //log.info("Search Query == ",queryBuilder.build().getQuery().toString());
        return operations.search(query, JSONObject.class, index);
    }
}
