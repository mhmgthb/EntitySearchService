package sa.qiwa.cache.search.ms.domain.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONObject;


import java.io.Serializable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import jakarta.annotation.Generated;

/**
 * Response
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SearchResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("totalRecords")
  private Long totalRecords;

  @JsonProperty("records")
  @Valid
  private List<JSONObject> records = new ArrayList<>();

  public SearchResponse totalRecords(Long totalRecords) {
    this.totalRecords = totalRecords;
    return this;
  }

  /**
   * Get totalRecords
   * @return totalRecords
  */
  @NotNull 
  public Long getTotalRecords() {
    return totalRecords;
  }

  public void setTotalRecords(Long totalRecords) {
    this.totalRecords = totalRecords;
  }

  public SearchResponse records(List<JSONObject> records) {
    this.records = records;
    return this;
  }

  public SearchResponse addRecordsItem(JSONObject recordsItem) {
    this.records.add(recordsItem);
    return this;
  }

  /**
   * Get records
   * @return records
  */
  @NotNull @Valid 
  public List<JSONObject> getRecords() {
    return records;
  }

  public void setRecords(List<JSONObject> records) {
    this.records = records;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchResponse response = (SearchResponse) o;
    return Objects.equals(this.totalRecords, response.totalRecords) &&
        Objects.equals(this.records, response.records);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalRecords, records);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Response {\n");
    sb.append("    totalRecords: ").append(toIndentedString(totalRecords)).append("\n");
    sb.append("    records: ").append(toIndentedString(records)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

