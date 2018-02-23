package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.SourceArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the SourceArchive entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceArchiveRepository extends JpaRepository<SourceArchive, Long>, JpaSpecificationExecutor<SourceArchive> {

}
