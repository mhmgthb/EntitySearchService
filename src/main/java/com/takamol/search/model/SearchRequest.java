package com.takamol.search.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Criteria
 */

public class SearchRequest {
  @JsonProperty("filters")
  @Valid
  private List<Filter> filters = new ArrayList<Filter>();

  @JsonProperty("orders")
  @Valid
  private List<Order> orders = new ArrayList<Order>();

  @JsonProperty("size")
  @Max(20)
  @Min(1)
  private Integer size = null;

  @JsonProperty("offset")
  private Integer offset = null;

  public SearchRequest filters(List<Filter> filters) {
    this.filters = filters;
    return this;
  }

  public SearchRequest addFiltersItem(Filter filtersItem) {
    this.filters.add(filtersItem);
    return this;
  }

  /**
   * Get filters
   * @return filters
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  public SearchRequest orders(List<Order> orders) {
    this.orders = orders;
    return this;
  }

  public SearchRequest addOrdersItem(Order ordersItem) {
    this.orders.add(ordersItem);
    return this;
  }

  /**
   * Get orders
   * @return orders
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull

  @Valid

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public SearchRequest size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * @return size
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public SearchRequest offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Get offset
   * @return offset
  **/
  @ApiModelProperty(required = true, value = "")
  @NotNull


  public Integer getOffset() {
    return offset;
  }

  public void setOffset(Integer offset) {
    this.offset = offset;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SearchRequest criteria = (SearchRequest) o;
    return Objects.equals(this.filters, criteria.filters) &&
        Objects.equals(this.orders, criteria.orders) &&
        Objects.equals(this.size, criteria.size) &&
        Objects.equals(this.offset, criteria.offset);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filters, orders, size, offset);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Criteria {\n");
    
    sb.append("    filters: ").append(toIndentedString(filters)).append("\n");
    sb.append("    orders: ").append(toIndentedString(orders)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
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

