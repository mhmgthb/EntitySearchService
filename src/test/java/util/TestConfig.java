package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.takamol.qiwa.microservice.search.model.Filter;
import com.takamol.qiwa.microservice.search.model.Order;
import com.takamol.qiwa.microservice.search.model.SearchRequest;
import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
//import org.json.JSONPointer;
import net.minidev.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public class TestConfig {
    public  static String  BASE_URI= "http://localhost:9001";

    /*public static String  getValidJsonRequest() throws JsonProcessingException{

        return convertToJsonString(getSearchRequest());

    }
*/
    public  static String convertToJsonString(SearchRequest searchRequest) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(searchRequest);
    }
    public static SearchRequest getSearchRequest(){
        SearchRequest searchRequest = new SearchRequest();
        Filter filter = new Filter();
        filter.setField("ContractPeriod");
        filter.setOperator(Filter.OperatorEnum.EQ);
        filter.setValue("12");
        searchRequest.getFilters().add(filter);

        Order order = new Order();
        order.setField("ContractPeriod2");
        order.setAsc(true);

        searchRequest.setSize(20);
        searchRequest.setOffset(0);

        return searchRequest;
    }
    public static JSONObject  getInvalidSchemaRequest(){
        //getSearchRequest()
        //JsonBuilder builder = new JsonBuilder();
        JSONObject jsonObject = new JSONObject();
        jsonObject.appendField("order","123");
        System.out.println("json object === "+jsonObject.toJSONString());
       // getValidJsonRequest()

        return jsonObject;
    }
    public  static  SearchRequest getRequestWithEmptyFilterField()  {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.getFilters().get(0).setField("");

       return searchRequest;

    }

    public  static  SearchRequest getRequestWithInvalidPageSize() {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.setSize(-1);

        return searchRequest;

    }
    public  static  SearchRequest getRequestWithInvalidOffsetSize() {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.setSize(-1);
        return searchRequest;

    }
    public  static  SearchRequest getRequestWithOrderFieldNulle() {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.getOrders().get(0).setField(null);
        return searchRequest;

    }
    public  static  SearchRequest getRequestWithOrderFieldInvalid()  {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.getOrders().get(0).setField("adfaadfas");
        //return convertToJsonString(searchRequest);
        return  searchRequest;

    }

}
