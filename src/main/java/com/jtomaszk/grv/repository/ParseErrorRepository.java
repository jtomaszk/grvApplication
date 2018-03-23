package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.ParseError;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the ParseError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParseErrorRepository extends JpaRepository<ParseError, Long>, JpaSpecificationExecutor<ParseError> {

}
