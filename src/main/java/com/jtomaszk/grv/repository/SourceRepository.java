package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.Source;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Source entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SourceRepository extends JpaRepository<Source, Long>, JpaSpecificationExecutor<Source> {

}
