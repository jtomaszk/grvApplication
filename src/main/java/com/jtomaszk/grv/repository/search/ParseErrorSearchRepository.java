package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.ParseError;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ParseError entity.
 */
public interface ParseErrorSearchRepository extends ElasticsearchRepository<ParseError, Long> {
}
