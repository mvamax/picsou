package io.picsou.repository;

import io.picsou.domain.Contrat;

import java.sql.Date;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ContratRepository extends JpaRepository<Contrat,Long>{
	
	List<Contrat> findAllByClientIdAndEtatContratId(Long clientId,Long etatContratId);
	
	List<Contrat> findAllByEtatContratId(Long etatContratId);
	
	List<Contrat> findByDateExecutionContratBeforeAndDateExecutionContratAfterOrDateExecutionContratIsNull(Date datemax,Date datemin);
	
	Long countByDateExecutionContratBeforeAndDateExecutionContratAfterOrDateExecutionContratIsNull(Date datemax,Date datemin);

	Long countByEtatContratId(Long etatContratId);
	
	@Query(value="select sum(prix) from t_contrat c where to_char(c.dateexecutioncontrat, 'YYYY')=?1 "
			+ "and c.etatcontrat_id=30"
			+ "",nativeQuery=true)
	Long sumPriceByYear(String year);
	
	
}
