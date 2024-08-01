package sa.qiwa.cache.search.ms.domain.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;

@Document(indexName = "establishment-profile-info")
//@Mapping(mappingPath = "establishment-mapping.json")
@Data
public class Establishment {
    @Id
    String id;
    private Date timestamp;
    @Field(name = "AdditionalNo")
    private Long additionalNo;
    @Field(name = "BuildingNO")
    private String buildingNO;
    @Field(name = "CRExpiryDateGregorian")
    private Date crExpiryDateGregorian;
    @Field(name = "CRExpiryDateHijri")
    private Long crExpiryDateHijri;
    @Field(name = "CRIssueDateGregorin")
    private Date crIssueDateGregorin;
    @Field(name = "CRIssueDateHijri")
    private  Date   crIssueDateHijri;
    @Field(name="CRNumber")
    private String crNumber;
    @Field(name = "CRStatus")
    private Long crStatus;
    @Field(name = "CityAr")
    private String cityAr;
    @Field(name = "CityEn")
    private String cityEn;
    @Field(name = "CityID")
    private Long cityID;
    @Field(name = "ContactEmail")
    private String contactEmail;
    @Field(name = "ContactFirstName")
    private String contactFirstName;
    @Field(name = "ContactLastName")
    private String contactLastName;
    @Field(name = "ContactMobileNumber")
    private String contactMobileNumber;
    @Field(name = "CreationDate")
    private Date creationDate;
    @Field(name = "District")
    private String district;
    @Field(name = "EconomicActivity")
    private String economicActivity;
    @Field(name = "EconomicActivityId")
    private String economicActivityId;
    @Field(name = "EstablishmentId")
    private Long establishmentId;
    @Field(name = "EstablishmentManagersList")
    private List<String> establishmentManagersList;
    @Field(name = "EstablishmentName")
    private String establishmentName;
    @Field(name = "EstablishmentTypeAr")
    private String establishmentTypeAr;
    @Field(name = "EstablishmentTypeEn")
    private String establishmentTypeEn;
    @Field(name = "EstablishmentTypeId")
    private Long establishmentTypeId;
    @Field(name = "EstablishmentTypeMCIAr")
    private String establishmentTypeMCIAr;
    @Field(name = "EstablishmentTypeMCIEn")
    private String establishmentTypeMCIEn;
    @Field(name = "EstablishmentTypeMCIID")
    private Long establishmentTypeMCIID;
    @Field(name = "IsMain")
    private Long isMain;
    @Field(name = "Isic4ActivityAr")
    private String isic4ActivityAr;
    @Field(name = "Isic4ActivityId")
    private String isic4ActivityId;
    @Field(name = "LaborOfficeId")
    private Long laborOfficeId;
    @Field(name = "Latitude")
    private String latitude;
    @Field(name = "CrIssueDateGregorian")
    private Date crIssueDateGregorian;
    @Field(name = "LicenseList")
    private List<String> licenseList;
    @Field(name = "MlSDUserTypeId")
    private Long mlSDUserTypeId;
    @Field(name = "LocationsList")
    private List<String> locationsList;
    @Field(name = "Longitude")
    private String longitude;
    @Field(name = "NLSDUserTypeId")
    private Long nLSDUserTypeId;
    @Field(name = "NationalUnifiedNumber")
    private String nationalUnifiedNumber;
    @Field(name = "NationalityAr")
    private String nationalityAr;
    @Field(name = "NationalityEn")
    private String nationalityEn;
    @Field(name = "NationalityId")
    private Long nationalityId;
    @Field(name = "OwnersList")
    private String ownersList;
    @Field(name = "SequenceNumber")
    private Long sequenceNumber;
    @Field(name = "SevenHundredNumber")
    private Long sevenHundredNumber;
    @Field(name = "StatusAr")
    private String statusAr;
    @Field(name = "StatusEn")
    private String statusEn;
    @Field(name = "FK_Isic4activityID")
    private  Long fk_Isic4activityID;
    @Field(name = "FK_UnifiedNumberID")
    private  Long fK_UnifiedNumberID;
    @Field(name = "StatusId")
    private Long statusId;
    @Field(name = "StreetName")
    private String streetName;
    @Field(name = "UNLaborOfficeId")
    private Long unLaborOfficeId;
    @Field(name = "UNSequenceNumber")
    private Long unSequenceNumber;
    @Field(name = "UnifiedNumberId")
    private Long unifiedNumberId;
    @Field(name = "UnifiedNumberManager")
    private String unifiedNumberManager;
    @Field(name = "UnifiedNumberManagerId")
    private String unifiedNumberManagerId;
    @Field(name = "ZipCode")
    private String zipCode;
    private String location;
    private String description;
    private List<String> tags;
    private Long title;

}
