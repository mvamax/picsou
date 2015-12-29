package io.picsou.service;

import java.util.List;

import io.picsou.domain.ParametreImposition;
import io.picsou.repository.ParametreImpositionRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametresTauxImpositionService {

	private final Logger log = LoggerFactory
			.getLogger(ParametresTauxImpositionService.class);
	
	@Autowired
	ParametreImpositionRepository parametreImpositionRepository;
	
	public ParametreImposition getParametreImpositionAndCreateIfNotExistByYear(String year){
		ParametreImposition res = parametreImpositionRepository.findAllByYear(year).get(0);
		if(res==null){
			log.info("creation du parametre d'imposition pour l'année "+year.toString());
			ParametreImposition pimpotnouveau=new ParametreImposition();
			pimpotnouveau.setTaux(20);
			pimpotnouveau.setYear(year);
			pimpotnouveau=parametreImpositionRepository.save(pimpotnouveau);
			return pimpotnouveau;
		}
		return res;
	}
	
	public void save(ParametreImposition pi){
		parametreImpositionRepository.save(pi);
	}
	
	public List<ParametreImposition> findAll(){
		return parametreImpositionRepository.findAll();
	}
	
}
