package sa.qiwa.cache.search.ms.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import sa.qiwa.cache.search.ms.application.delegator.SearchApiDelegate;


import org.springframework.beans.factory.annotation.Autowired;
import sa.qiwa.cache.search.ms.presentation.api.SearchApi;

import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
@RequestMapping("${openapi.entitySearchService.base-path:}")
@Slf4j
public class SearchApiController implements SearchApi {

    private final SearchApiDelegate delegate;

    public SearchApiController(@Autowired(required = false) SearchApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new SearchApiDelegate() {});
    }

    @Override
    public SearchApiDelegate getDelegate() {
        return delegate;
    }

}
