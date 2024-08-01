package sa.qiwa.cache.aggregate.ms.domain.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.math.BigDecimal;
import org.openapitools.jackson.nullable.JsonNullable;
import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Aggregation
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Aggregation implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("metric")
  private String metric;

  @JsonProperty("metricValue")
  private Double metricValue;

  public Aggregation metric(String metric) {
    this.metric = metric;
    return this;
  }

  /**
   * Get metric
   * @return metric
  */
  @NotNull 
  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Aggregation metricValue(Double metricValue) {
    this.metricValue = metricValue;
    return this;
  }

  /**
   * Get metricValue
   * @return metricValue
  */
  @NotNull @Valid 
  public Double getMetricValue() {
    return metricValue;
  }

  public void setMetricValue(Double metricValue) {
    this.metricValue = metricValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Aggregation aggregation = (Aggregation) o;
    return Objects.equals(this.metric, aggregation.metric) &&
        Objects.equals(this.metricValue, aggregation.metricValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(metric, metricValue);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Aggregation {\n");
    sb.append("    metric: ").append(toIndentedString(metric)).append("\n");
    sb.append("    metricValue: ").append(toIndentedString(metricValue)).append("\n");
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

