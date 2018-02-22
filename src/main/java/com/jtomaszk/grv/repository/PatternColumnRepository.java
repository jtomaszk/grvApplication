package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.PatternColumn;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PatternColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternColumnRepository extends JpaRepository<PatternColumn, Long>, JpaSpecificationExecutor<PatternColumn> {

}
