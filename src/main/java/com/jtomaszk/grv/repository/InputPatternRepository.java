package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.InputPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the InputPattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InputPatternRepository extends JpaRepository<InputPattern, Long>, JpaSpecificationExecutor<InputPattern> {

}
