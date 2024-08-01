package sa.qiwa.cache.aggregate.ms.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * AggregateRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AggregateRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("searchRequest")
  private SearchRequest searchRequest;

  /**
   * Gets or Sets aggregation
   */
  public enum AggregationEnum {
    COUNT("count"),
    
    AVG("avg"),
    
    MIN("min"),
    
    MAX("max"),

    STATS("stats");

    private String value;

    AggregationEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AggregationEnum fromValue(String value) {
      for (AggregationEnum b : AggregationEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("aggregation")
  private AggregationEnum aggregation;
  @NotNull
  @NotEmpty
  @JsonProperty("aggregationField")
  private String aggregationField;

  public AggregateRequest searchRequest(SearchRequest searchRequest) {
    this.searchRequest = searchRequest;
    return this;
  }

  /**
   * Get searchRequest
   * @return searchRequest
  */
  @Valid 
  public SearchRequest getSearchRequest() {
    return searchRequest;
  }

  public void setSearchRequest(SearchRequest searchRequest) {
    this.searchRequest = searchRequest;
  }

  public AggregateRequest aggregation(AggregationEnum aggregation) {
    this.aggregation = aggregation;
    return this;
  }

  /**
   * Get aggregation
   * @return aggregation
  */
  @NotNull
  public AggregationEnum getAggregation() {
    return aggregation;
  }

  public void setAggregation(AggregationEnum aggregation) {
    this.aggregation = aggregation;
  }

  public AggregateRequest aggregationField(String aggregationField) {
    this.aggregationField = aggregationField;
    return this;
  }

  /**
   * Get aggregationField
   * @return aggregationField
  */
  @NotNull 
  public String getAggregationField() {
    return aggregationField.replaceAll("\\\\","");
  }

  public void setAggregationField(String aggregationField) {
    this.aggregationField = aggregationField;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggregateRequest aggregateRequest = (AggregateRequest) o;
    return Objects.equals(this.searchRequest, aggregateRequest.searchRequest) &&
        Objects.equals(this.aggregation, aggregateRequest.aggregation) &&
        Objects.equals(this.aggregationField, aggregateRequest.aggregationField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(searchRequest, aggregation, aggregationField);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggregateRequest {\n");
    sb.append("    searchRequest: ").append(toIndentedString(searchRequest)).append("\n");
    sb.append("    aggregation: ").append(toIndentedString(aggregation)).append("\n");
    sb.append("    aggregationField: ").append(toIndentedString(aggregationField)).append("\n");
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

