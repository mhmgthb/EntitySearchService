package sa.qiwa.cache.aggregate.ms.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * AggregateResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class AggregateResponse implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("docCount")
  private BigDecimal docCount;

  @JsonProperty("aggregations")
  @Valid
  private List<Aggregation> aggregations = new ArrayList<>();

  public AggregateResponse docCount(BigDecimal docCount) {
    this.docCount = docCount;
    return this;
  }

  /**
   * Get docCount
   * @return docCount
  */
  @NotNull @Valid 
  public BigDecimal getDocCount() {
    return docCount;
  }

  public void setDocCount(BigDecimal docCount) {
    this.docCount = docCount;
  }

  public AggregateResponse aggregations(List<Aggregation> aggregations) {
    this.aggregations = aggregations;
    return this;
  }

  public AggregateResponse addAggregationsItem(Aggregation aggregationsItem) {
    this.aggregations.add(aggregationsItem);
    return this;
  }

  /**
   * Get aggregations
   * @return aggregations
  */
  @NotNull @Valid 
  public List<Aggregation> getAggregations() {
    return aggregations;
  }

  public void setAggregations(List<Aggregation> aggregations) {
    this.aggregations = aggregations;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AggregateResponse aggregateResponse = (AggregateResponse) o;
    return Objects.equals(this.docCount, aggregateResponse.docCount) &&
        Objects.equals(this.aggregations, aggregateResponse.aggregations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(docCount, aggregations);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AggregateResponse {\n");
    sb.append("    docCount: ").append(toIndentedString(docCount)).append("\n");
    sb.append("    aggregations: ").append(toIndentedString(aggregations)).append("\n");
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

