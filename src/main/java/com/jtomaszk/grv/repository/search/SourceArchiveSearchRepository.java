package com.jtomaszk.grv.repository.search;

import com.jtomaszk.grv.domain.SourceArchive;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SourceArchive entity.
 */
public interface SourceArchiveSearchRepository extends ElasticsearchRepository<SourceArchive, Long> {
}
