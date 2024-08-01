package sa.qiwa.cache.search.ms.test;


import jdk.jfr.Timestamp;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;
import java.util.List;


@Data
public class Car {
    private Date timestamp;
    private Long additionalNo;
    private String buildingNO;
    private Date crExpiryDateGregorian;
    private Long crExpiryDateHijri;
    private Date crIssueDateGregorin;
    private  Date   crIssueDateHijri;
    private String crNumber;
    private Long crStatus;
    private String cityAr;
    private String cityEn;
    private Long cityID;
    private String contactEmail;
    private String contactFirstName;
    private String contactLastName;
    private String contactMobileNumber;
    private Date creationDate;
    private String district;
    private String economicActivity;
    private String economicActivityId;
    private Long establishmentId;
    private String establishmentManagersList;
    private String establishmentName;
    private String establishmentTypeAr;
    private String establishmentTypeEn;
    private Long establishmentTypeId;
    private String establishmentTypeMCIAr;
    private String establishmentTypeMCIEn;
    private Long establishmentTypeMCIID;
    private Long isMain;
    private String isic4ActivityAr;
    private String isic4ActivityId;
    private Long laborOfficeId;
    private String latitude;
    private Date crIssueDateGregorian;
    private String licenseList;
    private Long mlSDUserTypeId;
    private String locationsList;
    private String longitude;
    private Long nLSDUserTypeId;
    private String nationalUnifiedNumber;
    private String nationalityAr;
    private String nationalityEn;

    private Long nationalityId;
    private String ownersList;
    private Long sequenceNumber;
    private Long sevenHundredNumber;
    private String statusAr;
    private String statusEn;
    private  Long fk_Isic4activityID;
    private  Long fK_UnifiedNumberID;
    private Long statusId;
    private String streetName;
    private Long unLaborOfficeId;
    private Long unSequenceNumber;
    private Long unifiedNumberId;
    private String unifiedNumberManager;
    private String unifiedNumberManagerId;

    private String zipCode;
    private String id;
    private String location;
    private String description;
    private List<String> tags;
    private Long title;
}

