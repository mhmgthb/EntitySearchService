package com.takamol.qiwa.microservice.search.text;

public class ServiceMessage {
    public static final String BUSINESS_VALIDATION_ELASTICSEARCH = "Elasticsearch throew exception while executing query, this may be due to unmatched field in the query and index";
    public static final String  BAD_REQUEST = "Submitted request is not a valid request ";
    public  static  final String INTERNAL_SERVER_ERROR = "Service is currently down, please try again later.";
}
