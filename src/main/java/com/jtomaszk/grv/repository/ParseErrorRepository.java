package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.ParseError;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ParseError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParseErrorRepository extends JpaRepository<ParseError, Long>, JpaSpecificationExecutor<ParseError> {

}
