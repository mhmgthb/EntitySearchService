package sa.qiwa.cache.search.ms.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;


import jakarta.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Gets or Sets EntityName
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public enum EntityName {
  
  CONTRACTS("Contracts"),
  
  DOCUMENTS("Documents"),
  
  EMPLOYEES("Employees");

  private String value;

  EntityName(String value) {
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
  public static EntityName fromValue(String value) {
    for (EntityName b : EntityName.values()) {
      if (b.value.equals(value)) {
        return b;
      }
    }
    throw new IllegalArgumentException("Unexpected value '" + value + "'");
  }
}

