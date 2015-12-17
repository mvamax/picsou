package io.picsou.repository;

import io.picsou.domain.CatalogueType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogueTypeRepository extends JpaRepository<CatalogueType,Long>{

}