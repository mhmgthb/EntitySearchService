package sa.qiwa.cache.aggregate.ms.infrastructure.repository;

import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.FunctionScore;
import co.elastic.clients.json.JsonData;
import co.elastic.clients.util.ObjectBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.core.AggregationContainer;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveSearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import sa.qiwa.cache.aggregate.ms.domain.model.SearchRequest;
import sa.qiwa.cache.aggregate.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.aggregate.ms.domain.model.AggregateRequest;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@Component
public class AggregateRepositoryImpl implements AggregateRepository {

    ReactiveElasticsearchOperations operations;

    //ReactiveElasticsearchClient client;
    AggregateRepositoryImpl(ReactiveElasticsearchOperations operations){
        this.operations = operations;
    }
    @SneakyThrows
    public Mono<ReactiveSearchHits<JSONObject>> aggregate(final String entityName, final AggregateRequest aggregateRequest) {
       log.info("in repository aggregate method {} ",CommonConstants.entityNameIndexMap.get(entityName));
        IndexCoordinates index = IndexCoordinates.of(CommonConstants.entityNameIndexMap.get(entityName));
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withRequestCache(true).withTrackTotalHits(true);//.withMaxResults(0);

        //Query query = this.buildSearchQuery(queryBuilder,aggregateRequest);

        Query query = queryBuilder.withAggregation(CommonConstants.AGGREGATE_METRIC,this.buildAggregations(aggregateRequest)).build();


        return operations.searchForHits(query, JSONObject.class, index);
    }

private Aggregation buildAggregations(AggregateRequest aggregateRequest){
    Aggregation aggregation = null;

//Aggregation.of(builder -> builder.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField())))
    //Aggregation.of(builder -> builder.stats(builder1 -> builder1.field(aggregateRequest.getAggregationField())));
    log.info("Aggregation field: {}",aggregateRequest.getAggregationField());
    //TopMetricsAggregation.Builder bjuilder = AggregationBuilders.topMetrics().metrics(builder -> builder.field(aggregateRequest.getAggregationField()));
    //FiltersAggregation.of(builder -> builder.filters(queryBuilder -> queryBuilder.))
    Map<String, Aggregation> aggregationMap = new HashMap<>(2);
    switch (aggregateRequest.getAggregation()) {
        //case COUNT ->{
            //aggregation = Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField())));
            //Aggregation. Builder. ContainerBuilder   containerBuilder = null;
                     /*aggregation = Aggregation.of(builder -> {
                        Aggregation. Builder. ContainerBuilder containerBuilder = builder.filter(q -> buildAggregateQuery(q, aggregateRequest.getSearchRequest()));
                        if(!isNestedField(aggregateRequest.getAggregationField()))

                            containerBuilder.aggregations(aggregateRequest.getAggregation().getValue(), Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField()))));
                        else {
                            String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
                            containerBuilder.aggregations(aggregateRequest.getAggregation().getValue(), Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField()))));
                        }
                        return  containerBuilder;
                    });
            return  aggregation;*/
           // aggregation = Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField())));

            //aggregation = AggregationBuilders.sum(builder -> builder.field(aggregateRequest.getAggregationField()));

            //builder.aggregations("sum", Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField()))))

                    //);
              //  }
            //aggregation = Aggregation.of(
                   // builder -> //{
                        /*builder.filter(q -> q.bool(b -> b.must(m -> m.term(t -> t
                                .field("LaborerIdNo")
                                .value("1043215860")))));*/

                        //builder.aggregations(aggregateRequest.getAggregation().getValue(), Aggregation.of(builder1 -> builder1.sum(builder2 -> builder2.field(aggregateRequest.getAggregationField()))));
                       // return builder;
                    //}
            //);
        //Aggregation.of(builder -> builder.sum(builder1 -> builder1.field("ContractPeriod")));
        //AggregationBuilders.nested(field.getName() + "Nested",field.getNestedPath())
                //.subAggregation(lastAgg);
        //}
        //case COUNT -> aggregation = Aggregation.of(builder -> builder.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField())));
        /*case COUNT -> {
            //AggregationBuilders.nested().path().
            Aggregation agg = Aggregation.of(builder2 -> builder2.sum(builder1 -> builder1
                    .field(aggregateRequest.getAggregationField().replaceAll("\\\\",""))));


            Aggregation agg1 = Aggregation.of(builder -> {
                Aggregation.Builder.ContainerBuilder containerBuilder = builder.nested(builder1 -> builder1.path("RelatedTo"));
                       containerBuilder.aggregations("count",agg);

                return  containerBuilder;
            });//.aggregations("count",agg));
            //agg.aggregations().put("count",agg1);"RelatedTo"
            //AggregationBuilders.nested(aggregateRequest.getAggregation().getValue() + "Nested","RelatedTo")
                   // .subAggregation(agg);

            aggregation = Aggregation.of(builder ->
                    {
                        Aggregation.Builder.ContainerBuilder containerBuilder = builder.filter(q -> buildAggregateQuery(q, aggregateRequest.getSearchRequest()));
                        Aggregation sumAgg = Aggregation.of(builder2 -> builder2.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField())));
                        if(isNestedField(aggregateRequest.getAggregationField())){
                            String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
                            containerBuilder.aggregations("nested_agg", Aggregation.of(builder1 ->builder1.nested(builder2 -> builder2.path(nestedPath)).aggregations(aggregateRequest.getAggregation().getValue(),sumAgg)));
                        }else{
                            containerBuilder.aggregations(aggregateRequest.getAggregation().getValue(),sumAgg);
                        }

                        //containerBuilder.aggregations("nested_agg",agg1);//.aggregations("count_agg",agg);
                        //.aggregations("nested_agg", agg1));
                        return containerBuilder;
                    });
            //Aggregation.of(builder2 -> builder2.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField()))


                           *//* {
                                    Aggregation.Builder.ContainerBuilder containerBuilder = null;
            containerBuilder = builder2.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField()));
            if (isNestedField(aggregateRequest.getAggregationField())) {
                String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
                log.debug("Nested path: {}", nestedPath);
                containerBuilder = builder2.nested(builder1 -> builder1.path(nestedPath));
            }
            return containerBuilder;
                })).
            aggregations("nested_agg", Aggregation.of(builder2 -> builder2.nested(builder1 -> builder1.field(aggregateRequest.getAggregationField())))))*//*
            ;
        }*/
        case COUNT -> aggregationMap.put(aggregateRequest.getAggregation().getValue(),Aggregation.of(builder -> builder.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField()))));
        case AVG ->   aggregationMap.put(aggregateRequest.getAggregation().getValue(),Aggregation.of(builder -> builder.avg(builder1 -> builder1.field(aggregateRequest.getAggregationField()))));
        case MAX ->  aggregationMap.put(aggregateRequest.getAggregation().getValue(),Aggregation.of(builder -> builder.max(builder1 -> builder1.field(aggregateRequest.getAggregationField()))));
        case MIN ->  aggregationMap.put(aggregateRequest.getAggregation().getValue(),Aggregation.of(builder -> builder.min(builder1 -> builder1.field(aggregateRequest.getAggregationField()))));
        case STATS ->  aggregationMap.put(aggregateRequest.getAggregation().getValue(),Aggregation.of(builder -> builder.stats(builder1 -> builder1.field(aggregateRequest.getAggregationField()))));

        // default -> throw new IllegalArgumentException("Aggregation not supported");
    }

    aggregation = Aggregation.of(builder ->
    {
        Aggregation.Builder.ContainerBuilder containerBuilder = builder.filter(q -> buildAggregateQuery(q, aggregateRequest.getSearchRequest()));
        if(isNestedField(aggregateRequest.getAggregationField())){
            String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
            containerBuilder.aggregations(CommonConstants.NESTED_AGGREGATE, Aggregation.of(builder1 -> {

                Aggregation. Builder. ContainerBuilder nestedBuilder =  builder1.nested(builder2 -> builder2.path(nestedPath));
                nestedBuilder.aggregations(aggregateRequest.getAggregation().getValue(),aggregationMap.get(aggregateRequest.getAggregation().getValue()));
                return nestedBuilder;
        }));

        }else{
            containerBuilder.aggregations(aggregateRequest.getAggregation().getValue(),aggregationMap.get(aggregateRequest.getAggregation().getValue()));
        }
        return containerBuilder;
    });

    /*if(isNestedField(aggregateRequest.getAggregationField())){
        String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
        aggregation = Aggregation.of(builder1 -> {

            Aggregation. Builder. ContainerBuilder nestedBuilder =  builder1.nested(builder2 -> builder2.path(nestedPath));
            nestedBuilder.aggregations(CommonConstants.NESTED_AGGREGATE  ,  Aggregation.of(filterBuilder -> filterBuilder.filter(q -> buildAggregateQuery(q, aggregateRequest.getSearchRequest()))));
            nestedBuilder.aggregations(aggregateRequest.getAggregation().getValue(),aggregationMap.get(aggregateRequest.getAggregation().getValue()));
            return nestedBuilder;
        });

    }*/

    /*aggregation = Aggregation.of(builder1 ->
    {
        Aggregation.Builder.ContainerBuilder builder = builder1.nested(builder2 -> builder2.path(getNestedPath(aggregateRequest.getAggregationField())));
        builder.aggregations("filter_agg",Aggregation.of(builder2 ->builder2.filter(q -> buildAggregateQuery(q, aggregateRequest.getSearchRequest()))));
        builder.aggregations(aggregateRequest.getAggregation().getValue(),aggregationMap.get(aggregateRequest.getAggregation().getValue()));
        return  builder;
    });*/
    return aggregation;
}
private Aggregation getAggregation(AggregateRequest aggregateRequest){

    if (isNestedField(aggregateRequest.getAggregationField())) {
        String nestedPath = getNestedPath(aggregateRequest.getAggregationField());
        return Aggregation.of(builder -> builder.nested(builder1 -> builder1.path(nestedPath)));//.subAggregation(aggregation);)

    }else{
        return Aggregation.of(builder2 -> builder2.sum(builder1 -> builder1.field(aggregateRequest.getAggregationField())));
    }

}
private ObjectBuilder<co.elastic.clients.elasticsearch._types.query_dsl.Query> buildAggregateQuery(
        co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder   q, SearchRequest searchRequest){
    return q.bool(b -> {
                        searchRequest.getFilters().stream().forEach(filter -> {
                            switch (filter.getOperator()) {
                                case EQ->
                                        b.must(m -> m.term(t -> t
                                                .field(filter.getField())
                                                .value(filter.getValue())));
                                case NE->
                                        b.mustNot(m -> m.term(t -> t
                                                .field(filter.getField())
                                                .value(filter.getValue())));
                                case GT->  b.must(m -> m.range(rng -> rng
                                        .field(filter.getField())
                                        .gt(JsonData.of(filter.getValue()))));
                                case LT->
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .lt(JsonData.of(filter.getValue()))));

                                case GTE->
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .gte(JsonData.of(filter.getValue()))));

                                case LTE->
                                        b.must(m -> m.range(rng -> rng
                                                .field(filter.getField())
                                                .lte(JsonData.of(filter.getValue()))));

                                case ANY->
                                        b.should(m -> m.term(t -> t
                                                //.field(filter.getField()).terms(trms->trms.value())
                                                .field(filter.getField())
                                                .value(filter.getValue())));
                                case BETWEEN-> {

                                    b.must(m -> m.range(rng -> rng
                                            .field(filter.getField().split(" ")[0])
                                            .gte(JsonData.of(filter.getValue()))));
                                    b.must(m -> m.range(rng -> rng
                                            .field(filter.getField().split(" ")[1])
                                            .lte(JsonData.of(filter.getValue()))));
                                }
                                case NOT_BETWEEN-> {

                                    b.must(m -> m.range(rng -> rng
                                            .field(filter.getField().split(" ")[0])
                                            .lte(JsonData.of(filter.getValue()))));
                                    b.must(m -> m.range(rng -> rng
                                            .field(filter.getField().split(" ")[1])
                                            .gte(JsonData.of(filter.getValue()))));
                                }

                            }

                        });
                        return b;
                    }

            );
}
    private void prepareSearchRequestForGenericAggregation(final String indexName,
                                                                    final AggregateRequest request,
                                                                    final Aggregation aggregation) {

        if (isNestedField(request.getAggregationField())) {
            String nestedPath = getNestedPath(request.getAggregationField());

            //aggregation.aggregations().put( AggregationBuilders.nested(builder -> builder.path(nestedPath).name("nested_agg")));//.subAggregation(aggregation);)

        }

    }

   /* private Object extractAggregationResult(Aggregations aggregations, String aggregationName) {
        org.elasticsearch.search.aggregations.Aggregation aggregation = aggregations.get(aggregationName);
        if (aggregation instanceof ValueCount) {
            return ((ValueCount) aggregation).getValue();
        } else if (aggregation instanceof Avg) {
            return ((Avg) aggregation).getValue();
        } else if (aggregation instanceof Min) {
            return ((Min) aggregation).getValue();
        } else if (aggregation instanceof Max) {
            return ((Max) aggregation).getValue();
        }
        // Handle other aggregation types as needed
        throw new IllegalArgumentException("Unsupported aggregation type");
    }

    private SearchRequest prepareElasticSearchRequest(final String indexName,
                                                      final SearchApiRequest request) {
        if (request == null) {
            return new SearchRequest().indices(indexName);
        }

        QueryBuilder queryBuilder = prepareQuery(request);

        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource()
                .query(queryBuilder)
                .from(request.getOffset())
                .size(request.getSize());

        if (!CollectionUtils.isEmpty(request.getOrders())) {
            SortBuilder<?> sortBuilder;
            for (SearchApiRequest.OrderRequest order : request.getOrders()) {
                sortBuilder = createNestedSortBuilder(order.getField(), order.isAsc());
                sourceBuilder.sort(sortBuilder);
            }
        }

        return new SearchRequest()
                .source(sourceBuilder)
                .indices(indexName);
    }

    private SortBuilder<?> createNestedSortBuilder(String fieldName, boolean ascending) {
        SortOrder sortOrder = ascending ? SortOrder.ASC : SortOrder.DESC;

        // Split the field name by dots to get all nested levels
        String[] nestedLevels = fieldName.split("\\.");
        NestedSortBuilder nestedSortBuilder = null;

        // Construct nested sort builders for each level, starting from the deepest level
        for (int i = nestedLevels.length - 2; i >= 0; i--) {
            String nestedPath = String.join(".", Arrays.copyOfRange(nestedLevels, 0, i + 1));
            NestedSortBuilder currentNestedSort = new NestedSortBuilder(nestedPath);

            if (nestedSortBuilder != null) {
                currentNestedSort.setNestedSort(nestedSortBuilder);
            }

            nestedSortBuilder = currentNestedSort;
        }

        // Create the final sort builder with the nested sort
        SortBuilder<?> sortBuilder = SortBuilders.fieldSort(fieldName).setNestedSort(nestedSortBuilder);
        sortBuilder.order(sortOrder);

        return sortBuilder;
    }

    private QueryBuilder prepareQuery(final SearchRequest request) {
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        if (!CollectionUtils.isEmpty(request.getFilters())) {
            for (SearchApiRequest.FilterRequest filter : request.getFilters()) {
                QueryBuilder filterQuery = createFilterQuery(filter);

                // Adjust the query for nested fields
                if (isNestedField(filter.getField())) {
                    String nestedPath = getNestedPath(filter.getField());
                    filterQuery = QueryBuilders.nestedQuery(nestedPath, filterQuery, ScoreMode.None);
                }

                queryBuilder.must(filterQuery);
            }
        }

        return queryBuilder;
    }

    private QueryBuilder createFilterQuery(SearchApiRequest.FilterRequest filter) {
        return switch (filter.getOperator()) {
            case EQUALS -> QueryBuilders.termQuery(filter.getField(), filter.getValue());
            case NOT_EQUALS -> QueryBuilders.boolQuery().mustNot(QueryBuilders.termQuery(filter.getField(), filter.getValue()));
            case ANY -> QueryBuilders.matchQuery(filter.getField(), filter.getValue()); // Assuming 'ANY' means 'match'
            case GREATER_THAN -> QueryBuilders.rangeQuery(filter.getField()).gt(filter.getValue());
            case GREATER_THAN_OR_EQUAL -> QueryBuilders.rangeQuery(filter.getField()).gte(filter.getValue());
            case LESS_THAN -> QueryBuilders.rangeQuery(filter.getField()).lt(filter.getValue());
            case LESS_THAN_OR_EQUAL -> QueryBuilders.rangeQuery(filter.getField()).lte(filter.getValue());
            case BETWEEN -> {
                String[] rangeValues = ((String) filter.getValue()).split(":");
                yield QueryBuilders.rangeQuery(filter.getField()).gte(rangeValues[0]).lte(rangeValues[1]);
            }
            case NOT_BETWEEN -> {
                String[] rangeValues = ((String) filter.getValue()).split(":");
                yield QueryBuilders.boolQuery().mustNot(QueryBuilders.rangeQuery(filter.getField()).gte(rangeValues[0]).lte(rangeValues[1]));
            }
            default -> throw new IllegalArgumentException("Unsupported operator: " + filter.getOperator());
        };
    }*/

    private Query buildSearchQuery(NativeQueryBuilder queryBuilder, AggregateRequest aggregateRequest){
        //queryBuilder.withAggregation(CommonConstants.AGGREGATE_METRIC,Aggregation.of(b -> b.valueCount(t -> t.field(aggregateRequest.getAggregationField()))));
        SearchRequest searchRequest = aggregateRequest.getSearchRequest();
        queryBuilder.withFilter( q -> q
                        .bool(b -> {
                            searchRequest.getFilters().stream().forEach(filter -> {
                                        switch (filter.getOperator()) {
                                            case EQ->
                                                    b.must(m -> m.term(t -> t
                                                            .field(filter.getField())
                                                            .value(filter.getValue())));
                                            case NE->
                                                    b.mustNot(m -> m.term(t -> t
                                                            .field(filter.getField())
                                                            .value(filter.getValue())));
                                            case GT->  b.must(m -> m.range(rng -> rng
                                                    .field(filter.getField())
                                                    .gt(JsonData.of(filter.getValue()))));
                                            case LT->
                                                    b.must(m -> m.range(rng -> rng
                                                            .field(filter.getField())
                                                            .lt(JsonData.of(filter.getValue()))));
                                            //break;
                                            case GTE->
                                                    b.must(m -> m.range(rng -> rng
                                                            .field(filter.getField())
                                                            .gte(JsonData.of(filter.getValue()))));
                                            //break;
                                            case LTE->
                                                    b.must(m -> m.range(rng -> rng
                                                            .field(filter.getField())
                                                            .lte(JsonData.of(filter.getValue()))));
                                            //break;
                                            case ANY->
                                                    b.should(m -> m.term(t -> t
                                                            //.field(filter.getField()).terms(trms->trms.value())
                                                            .field(filter.getField())
                                                            .value(filter.getValue())));

                                            //break;
                                        }

                                    });
                                    return b;
                                }

                        ));

       /* searchRequest.getOrders().forEach(order -> {
            queryBuilder.withSort(SortOptionsBuilders.field(builder -> builder.
                    field(order.getField()).
                    order(order.getAsc()? SortOrder.Asc:SortOrder.Desc)));
        });

        if(searchRequest.getFields() != null && !searchRequest.getFields().isEmpty()){
            queryBuilder.withSourceFilter(new FetchSourceFilter(searchRequest.getFields().toArray(new String[]{}),new String[]{"meta._id"}));

        }*/
        //queryBuilder.withPageable(Pageable.ofSize(1));
        //Pageable.ofSize(1)

        queryBuilder.withAggregation("agg_metrices",this.buildAggregations(aggregateRequest));

        /*queryBuilder.withAggregation("agg_metrices",
                Aggregation.of(b -> b.terms(t -> t.field(aggregateRequest.getAggregationField()))))
                .build();*/

       // queryBuilder.withAggregation("agg_stats",
            //    Aggregation.of(b -> b.valueCount(t -> t.field(aggregateRequest.getAggregationField()))));

        return queryBuilder.build();

    }
    private boolean isNestedField(String fieldName) {
        // Returns true if the field is considered nested
        return fieldName.contains(".");
    }

    private String getNestedPath(String fieldName) {
        // Extracts the nested path for nested fields

            int dotIndex = fieldName.indexOf('.');
            return (dotIndex > 0) ? fieldName.substring(0, dotIndex) : fieldName;


    }
}
