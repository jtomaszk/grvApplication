package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.LocationImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the LocationImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LocationImageRepository extends JpaRepository<LocationImage, Long> {

}
