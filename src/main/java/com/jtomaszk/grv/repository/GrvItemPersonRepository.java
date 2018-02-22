package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.GrvItemPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the GrvItemPerson entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrvItemPersonRepository extends JpaRepository<GrvItemPerson, Long>, JpaSpecificationExecutor<GrvItemPerson> {

}
