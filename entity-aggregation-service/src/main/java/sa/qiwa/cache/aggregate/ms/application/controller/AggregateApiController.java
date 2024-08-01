package sa.qiwa.cache.aggregate.ms.application.controller;

import sa.qiwa.cache.aggregate.ms.application.delegator.AggregateApiDelegate;
import sa.qiwa.cache.aggregate.ms.presentation.api.AggregateApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;


import jakarta.validation.constraints.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@Controller
@RequestMapping("${openapi.entitySearchService.base-path:}")
public class AggregateApiController implements AggregateApi {

    private final AggregateApiDelegate delegate;

    public AggregateApiController(@Autowired(required = false) AggregateApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new AggregateApiDelegate() {});
    }

    @Override
    public AggregateApiDelegate getDelegate() {
        return delegate;
    }

}
