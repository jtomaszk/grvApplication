package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.LocationImage;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LocationImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationImageRepository extends JpaRepository<LocationImage, Long> {

}
