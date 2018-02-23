package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.InputPattern;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InputPattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputPatternRepository extends JpaRepository<InputPattern, Long>, JpaSpecificationExecutor<InputPattern> {

}
