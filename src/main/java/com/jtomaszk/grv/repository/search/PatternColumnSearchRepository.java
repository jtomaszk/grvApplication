package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.PatternColumn;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PatternColumn entity.
 */
public interface PatternColumnSearchRepository extends ElasticsearchRepository<PatternColumn, Long> {
}
