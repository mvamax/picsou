package io.picsou.repository;

import io.picsou.domain.EtatContrat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtatContratRepository extends JpaRepository<EtatContrat, Long> {

}
