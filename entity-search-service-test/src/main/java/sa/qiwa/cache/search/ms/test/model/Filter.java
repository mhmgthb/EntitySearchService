package sa.qiwa.cache.search.ms.test.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.annotation.Generated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * Filter
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public class Filter implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotEmpty
  @NotNull
  @JsonProperty("field")
  private String field;

  /**
   * Gets or Sets operator
   */
  public enum OperatorEnum {
    EQ("eq"),
    
    NE("ne"),
    
    ANY("any"),
    
    GT("gt"),
    
    GTE("gte"),
    
    LT("lt"),
    
    LTE("lte"),
    
    BETWEEN("between"),
    
    NOT_BETWEEN("not_between");

    private String value;

    OperatorEnum(String value) {
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
    public static OperatorEnum fromValue(String value) {
      for (OperatorEnum b : OperatorEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  @JsonProperty("operator")
  private OperatorEnum operator;

  @JsonProperty("value")
  private String value;

  public Filter field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
  */
  @NotNull 
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public Filter operator(OperatorEnum operator) {
    this.operator = operator;
    return this;
  }

  /**
   * Get operator
   * @return operator
  */
  @NotNull 
  public OperatorEnum getOperator() {
    return operator;
  }

  public void setOperator(OperatorEnum operator) {
    this.operator = operator;
  }

  public Filter value(String value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
  */
  @NotNull 
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Filter filter = (Filter) o;
    return Objects.equals(this.field, filter.field) &&
        Objects.equals(this.operator, filter.operator) &&
        Objects.equals(this.value, filter.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, operator, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Filter {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    operator: ").append(toIndentedString(operator)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

