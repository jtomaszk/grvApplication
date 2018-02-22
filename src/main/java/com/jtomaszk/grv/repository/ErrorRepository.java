package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.Error;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Error entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ErrorRepository extends JpaRepository<Error, Long>, JpaSpecificationExecutor<Error> {

}
