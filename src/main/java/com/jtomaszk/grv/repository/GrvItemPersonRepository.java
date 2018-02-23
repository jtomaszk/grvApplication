package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.GrvItemPerson;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GrvItemPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrvItemPersonRepository extends JpaRepository<GrvItemPerson, Long>, JpaSpecificationExecutor<GrvItemPerson> {

}
