package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.PatternColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the PatternColumn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternColumnRepository extends JpaRepository<PatternColumn, Long>, JpaSpecificationExecutor<PatternColumn> {

}
