package io.picsou.repository;

import io.picsou.domain.ProduitContrat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProduitContratRepository extends JpaRepository<ProduitContrat,Long>{
	

}
