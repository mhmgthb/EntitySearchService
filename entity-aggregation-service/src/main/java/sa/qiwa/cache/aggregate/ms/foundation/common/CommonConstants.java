package sa.qiwa.cache.aggregate.ms.foundation.common;

import java.util.Map;

public class CommonConstants {
    public static final String ELASTICSEARCH_QUERY_EXCEPTION = "Elasticsearch throew exception while executing query, this may be due to unmatched field or value in the query and index";
    public static final String  BAD_REQUEST = "Submitted request is not a valid request ";
    public  static  final String INTERNAL_SERVER_ERROR = "Service is currently down, please try again later.";
    public static final Map<String,String> entityNameIndexMap = Map.of("Contracts","qiwa-contracts","Establishment","qiwa-establishment-profile");
    public static  final String ENTITY_NOT_FOUND = "Requested entity could not be found.";
    public  static  final  String ENTITY_NOT_IMPLEMNETED = "Search for requested enity is yet not implemented";


    public static final String MDC_KEY = "MDC_KEY";
    public static final String CONTEXT_KEY = "CONTEXT_KEY";
    public static final String X_REQUEST_ID = "X-Request-ID";

    public static final String AGGREGATE_METRIC = "agg_metric";
    public static final String NESTED_AGGREGATE = "nested_agg";
}
