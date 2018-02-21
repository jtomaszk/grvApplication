package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.Error;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Error entity.
 */
public interface ErrorSearchRepository extends ElasticsearchRepository<Error, Long> {
}
