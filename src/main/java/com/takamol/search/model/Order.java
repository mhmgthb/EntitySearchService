package com.takamol.search.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * Order
 */

public class Order   {
  @JsonProperty("field")
  private String field = null;

  @JsonProperty("asc")
  private Boolean asc = null;

  public Order field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Order asc(Boolean asc) {
    this.asc = asc;
    return this;
  }

  /**
   * Get asc
   * @return asc
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Boolean isAsc() {
    return asc;
  }

  public void setAsc(Boolean asc) {
    this.asc = asc;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return Objects.equals(this.field, order.field) &&
        Objects.equals(this.asc, order.asc);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, asc);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Order {\n");
    
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    asc: ").append(toIndentedString(asc)).append("\n");
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

