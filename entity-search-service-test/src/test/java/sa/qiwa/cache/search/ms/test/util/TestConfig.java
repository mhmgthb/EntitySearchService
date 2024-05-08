package sa.qiwa.cache.search.ms.test.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import net.minidev.json.JSONObject;
import sa.qiwa.cache.search.ms.test.model.Filter;
import sa.qiwa.cache.search.ms.test.model.Order;
import sa.qiwa.cache.search.ms.test.model.SearchRequest;


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
        filter.setField("LaborerIdNo");
        filter.setOperator(Filter.OperatorEnum.EQ);
        filter.setValue("1043215860");
        searchRequest.getFilters().add(filter);

        Order order = new Order();
        order.setField("CreationDate");
        order.setAsc(true);

        searchRequest.setSize(20);
        searchRequest.setOffset(0);

        return searchRequest;
    }
    public static  SearchRequest getSearchRequestWithAdditionalFilter(){
        SearchRequest searchRequest = getSearchRequest();
        Filter filter = new Filter();
        filter.setField("LastModifiedDate");
        filter.setOperator(Filter.OperatorEnum.LTE);
        filter.setValue("2023-12-12T01:05:01.830Z");
        searchRequest.getFilters().add(filter);

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