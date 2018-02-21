package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.Pattern;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pattern entity.
 */
public interface PatternSearchRepository extends ElasticsearchRepository<Pattern, Long> {
}
