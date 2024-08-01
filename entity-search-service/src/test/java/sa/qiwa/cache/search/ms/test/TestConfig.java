package sa.qiwa.cache.search.ms.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import sa.qiwa.cache.search.ms.domain.entity.Establishment;
import sa.qiwa.cache.search.ms.domain.model.Filter;
import sa.qiwa.cache.search.ms.domain.model.Order;
import sa.qiwa.cache.search.ms.domain.model.SearchRequest;
//import org.json.JSONPointer;
import net.minidev.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.List;

@Slf4j
public class TestConfig {
    public  static String  END_POINT= "/search";

    /*public static String  getValidJsonRequest() throws JsonProcessingException{

        return convertToJsonString(getSearchRequest());

    }
*/
    public static List<Establishment> getEstablishmentList(){

        try {
            //JSONArray jsonArray = (JSONArray)JSONValue.parse(new FileReader("establishments.json"));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<Establishment>> jacksonTypeReference = new TypeReference<List<Establishment>>() {};
//String dir = System.getProperty("user.dir");
//log.info("dir == {}", dir);
           //ClassPathResource classPathResource =  new ClassPathResource("establishments.json",TestConfig.class.getClassLoader());
            //log.info("ClassPath: {} ", classPathResource.exists());
            File resource = new ClassPathResource("establishments.json").getFile();
            /*String employees = new String(
                    Files.readAllBytes(resource.toPath()));*/
            List<Establishment> jacksonList = objectMapper.readValue(resource, jacksonTypeReference);

            jacksonList.forEach(establishment -> log.info("establishment ID : {}",establishment.getTimestamp()));

            //String jsonCarArray =
                    //"[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
           // List<Car> listCar = objectMapper.readValue(new File("E:\\Temp\\car.json"), new TypeReference<List<Car>>(){});
           // System.out.println("listcar size ....."+listCar.size());

            return jacksonList;

        }/* catch (FileNotFoundException e) {
            log.error("Error occurred when parsing json file.");
           log.error("File could not be found.",e);
        }*/catch (IOException e){
            log.error("Erro occurred when converting to java object.");
            log.error("Json is not valid.",e);
        }
        return null;
    }
    public  static String convertToJsonString(SearchRequest searchRequest)  {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(searchRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    public static SearchRequest getSearchRequest(){
        SearchRequest searchRequest = new SearchRequest();
        Filter filter = new Filter();
        filter.setField("EstablishmentName");
        filter.setOperator(Filter.OperatorEnum.EQ);
        filter.setValue("مؤسسة ايونات الشرق");
        searchRequest.getFilters().add(filter);

        Order order = new Order();
        order.setField("CreationDate");
        order.setAsc(true);

        searchRequest.getOrders().add(order);

        searchRequest.setSize(2);
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
    public  static  String getRequestWithEmptyFilterField()  {
        SearchRequest searchRequest = getSearchRequest();
        searchRequest.getFilters().get(0).setField("");
        return convertToJsonString(searchRequest);
       //return searchRequest;

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

    public static void main(String[] args) {
        TestConfig.getEstablishmentList();
    }
}
