package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.GrvItemPerson;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the GrvItemPerson entity.
 */
public interface GrvItemPersonSearchRepository extends ElasticsearchRepository<GrvItemPerson, Long> {
}
