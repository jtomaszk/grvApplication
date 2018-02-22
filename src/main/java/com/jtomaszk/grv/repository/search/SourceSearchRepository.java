package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.Source;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Source entity.
 */
public interface SourceSearchRepository extends ElasticsearchRepository<Source, Long> {
}
