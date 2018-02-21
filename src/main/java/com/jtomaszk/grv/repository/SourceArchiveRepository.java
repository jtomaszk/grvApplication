package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.SourceArchive;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SourceArchive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceArchiveRepository extends JpaRepository<SourceArchive, Long>, JpaSpecificationExecutor<SourceArchive> {

}
