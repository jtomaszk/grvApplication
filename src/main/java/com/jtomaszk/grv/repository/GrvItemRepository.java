package com.jtomaszk.grv.repository;

import com.jtomaszk.grv.domain.GrvItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the GrvItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GrvItemRepository extends JpaRepository<GrvItem, Long>, JpaSpecificationExecutor<GrvItem> {

}
