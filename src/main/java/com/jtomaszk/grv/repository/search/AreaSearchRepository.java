package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.Area;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Area entity.
 */
public interface AreaSearchRepository extends ElasticsearchRepository<Area, Long> {
}
