
package com.takamol.qiwa.microservice.search.api;

import com.takamol.qiwa.microservice.search.model.SearchRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import net.minidev.json.JSONObject;
//import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.List;

/*
@Validated
@Api(value = "search", description = "the search API")
//@RequestMapping(value = "/search")
public interface SearchApi {

    @ApiOperation(value = "Search entity using elastic search service", nickname = "searchEnity", notes = "Search entity using elastic search service", tags={ "search", })
    @ApiResponses(value = { 
        @ApiResponse(code = 400, message = "Bad Requesr"),
        @ApiResponse(code = 405, message = "Validation exception"),
        @ApiResponse(code = 500, message = "Internal server error"),
        @ApiResponse(code = 503, message = "Not implemented"),
        @ApiResponse(code = 201, message = "Success")})
    @RequestMapping(value = "/search/{entity}",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<?> searchEnity(@ApiParam(value = "",required=true) @PathVariable("entity") String entity, @ApiParam(value = "search Criteria will contain all filters and other criteria to search entity" ,required=true )  @Valid @RequestBody SearchRequest searchRequest);

}
*/
@Validated
public interface SearchApi {

    @Operation(summary = "Search entity using elastic search service", description = "Search entity using search service", tags={ "search" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Entity not found"),

            @ApiResponse(responseCode = "405", description = "Validation exception") })
    @RequestMapping(value = "/search/{entity}",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Mono<List<JSONObject>>> searchEnity(@Parameter(in = ParameterIn.PATH, description = "", required=true, schema=@Schema()) @PathVariable("entity") String entity
            , @Parameter(in = ParameterIn.DEFAULT, description = "search Criteria will contain all filters and other criteria to search entity", required=true, schema=@Schema()) @Valid @RequestBody SearchRequest body
    );

}