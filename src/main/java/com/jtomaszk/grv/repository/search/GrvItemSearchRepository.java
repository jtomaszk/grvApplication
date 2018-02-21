package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.GrvItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GrvItem entity.
 */
public interface GrvItemSearchRepository extends ElasticsearchRepository<GrvItem, Long> {
}
