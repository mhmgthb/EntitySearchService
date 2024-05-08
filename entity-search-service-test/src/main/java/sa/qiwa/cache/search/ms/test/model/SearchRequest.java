package sa.qiwa.cache.search.ms.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Generated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SearchRequest
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class SearchRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  @JsonProperty("fields")
  @Valid
  private List<String> fields = null;

  @JsonProperty("filters")
  @Valid
  private List<Filter> filters = new ArrayList<>();

  @JsonProperty("orders")
  @Valid
  private List<Order> orders = new ArrayList<>();


  @Range(min = 1,max=20)
  @JsonProperty("size")
  private Integer size;

  @Min(0)
  @JsonProperty("offset")
  private Integer offset;

  public SearchRequest fields(List<String> fields) {
    this.fields = fields;
    return this;
  }

  public SearchRequest addFieldsItem(String fieldsItem) {
    if (this.fields == null) {
      this.fields = new ArrayList<>();
    }
    this.fields.add(fieldsItem);
    return this;
  }

  /**
   * Get fields
   * @return fields
  */
  
  public List<String> getFields() {
    return fields;
  }

  public void setFields(List<String> fields) {
    this.fields = fields;
  }

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
  */
  @NotNull @Valid 
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
  */
  @NotNull @Valid 
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
   * minimum: 1
   * maximum: 50
   * @return size
  */
  @NotNull @Min(1) @Max(50) 
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
   * minimum: 0
   * @return offset
  */
  @NotNull @Min(0) 
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
    SearchRequest searchRequest = (SearchRequest) o;
    return Objects.equals(this.fields, searchRequest.fields) &&
        Objects.equals(this.filters, searchRequest.filters) &&
        Objects.equals(this.orders, searchRequest.orders) &&
        Objects.equals(this.size, searchRequest.size) &&
        Objects.equals(this.offset, searchRequest.offset);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields, filters, orders, size, offset);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SearchRequest {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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

