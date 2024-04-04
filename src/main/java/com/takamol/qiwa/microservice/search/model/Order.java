package com.takamol.qiwa.microservice.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * Order
 */

public class Order   {
  @JsonProperty("field")
  @NotNull
  @NotBlank
  private String field = null;

  @JsonProperty("asc")
  @NotNull
  private Boolean asc = null;

  public Order field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   **/
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
