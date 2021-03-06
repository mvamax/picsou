package io.picsou.repository;

import io.picsou.domain.Charge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends JpaRepository<Charge,Long>{

	@Query(value="select sum(cout) from t_charge c where to_char(c.date, 'YYYY')=?1 "
			+ "",nativeQuery=true)
	Long sumCountByYear(String year);
}
