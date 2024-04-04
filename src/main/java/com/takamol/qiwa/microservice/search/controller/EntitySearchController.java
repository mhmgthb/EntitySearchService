package com.takamol.qiwa.microservice.search.controller;

import com.takamol.qiwa.microservice.search.api.SearchApi;
import com.takamol.qiwa.microservice.search.delegator.EntitySearchDelegatorService;
import com.takamol.qiwa.microservice.search.model.SearchRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/search")
@Slf4j
public class EntitySearchController implements SearchApi {
        @Autowired
        private EntitySearchDelegatorService entitySearchDelegatorService;

       /* @PostMapping(value = "/{entityName}")
        public ResponseEntity<List<JSONObject>> searchEntity(@PathVariable String entity, @RequestBody @Valid SearchCriteria searchCriteria) {
                log.info("in searchEntity method of EntitySearchController");
                return new ResponseEntity<List<JSONObject>>(entitySearchService.searchEntity(searchCriteria),HttpStatus.OK);
        }*/

        //@Override
        @PostMapping(value = "/{entityName}")
        public ResponseEntity<Mono<List<JSONObject>>> searchEnity(@PathVariable String entityName, @Valid @RequestBody  SearchRequest searchRequest) {
                log.info("---.........----Request received in EntitySearchController................");
                //throw new EntitySearchException("DataAccessResourceFailureException Excetion occurred", Error.SERVER_ERROR);
                return new ResponseEntity<>(entitySearchDelegatorService.searchEntity(entityName,searchRequest), HttpStatus.CREATED);
        }

}

