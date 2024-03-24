package com.takamol.search.entity;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "qiwa-contracts-v2")
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BaseEntity{

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(type = FieldType.Text)
    /*String name;
    @Field(type = FieldType.Text)
    String status;*/

    private String contractId;
    private String contractPeriod;
    private String contractTypeNameAr;


}
