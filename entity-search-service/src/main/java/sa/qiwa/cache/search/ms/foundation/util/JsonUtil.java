package sa.qiwa.cache.search.ms.foundation.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import sa.qiwa.cache.search.ms.foundation.common.CommonConstants;
import sa.qiwa.cache.search.ms.foundation.enums.ErrorCodes;
import sa.qiwa.cache.search.ms.foundation.exception.ServiceException;

public class JsonUtil {
    public static String convertObjectToString(Object object){
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        }catch (JsonProcessingException jpe){
            throw new ServiceException(CommonConstants.INTERNAL_SERVER_ERROR, ErrorCodes.UNKNOWN);
        }
    }
}
