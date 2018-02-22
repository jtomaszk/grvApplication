package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.InputPattern;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the InputPattern entity.
 */
public interface InputPatternSearchRepository extends ElasticsearchRepository<InputPattern, Long> {
}
