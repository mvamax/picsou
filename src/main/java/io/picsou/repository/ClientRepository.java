package io.picsou.repository;

import io.picsou.domain.Client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{

	
	@Query(value="select count(*) from t_contrat ct where ct.client_id=?1 and ct.id=?2",nativeQuery=true)
	public Long verifieAppartenanceClientContrat(Long clientId, Long contratId);
	
}
