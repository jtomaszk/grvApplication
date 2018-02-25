package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.GrvItem;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GrvItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrvItemRepository extends JpaRepository<GrvItem, Long>, JpaSpecificationExecutor<GrvItem> {

}
