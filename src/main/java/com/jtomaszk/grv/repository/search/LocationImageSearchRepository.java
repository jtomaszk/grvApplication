package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.LocationImage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LocationImage entity.
 */
public interface LocationImageSearchRepository extends ElasticsearchRepository<LocationImage, Long> {
}
