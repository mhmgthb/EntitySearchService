package com.takamol.search.repository;

import com.takamol.search.entity.Employee;
import com.takamol.search.model.SearchRequest;
import org.springframework.data.elasticsearch.core.SearchHit;
import reactor.core.publisher.Flux;

public interface ReactiveEmployeeRepository {
    Flux<SearchHit<Employee>> searchEmployees(SearchRequest searchCriteria);
}
