package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.Area;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Area entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

}
