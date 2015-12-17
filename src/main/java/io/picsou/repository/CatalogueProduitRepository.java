package io.picsou.repository;

import io.picsou.domain.CatalogueProduit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CatalogueProduitRepository extends JpaRepository<CatalogueProduit,Long>{

}
