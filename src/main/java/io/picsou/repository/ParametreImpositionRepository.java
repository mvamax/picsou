package io.picsou.repository;

import io.picsou.domain.ParametreImposition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParametreImpositionRepository extends JpaRepository<ParametreImposition, Long>{

	List<ParametreImposition> findAllByYear(String year);
	
}
