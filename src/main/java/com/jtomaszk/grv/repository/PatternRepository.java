package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.Pattern;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pattern entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatternRepository extends JpaRepository<Pattern, Long> {

}
