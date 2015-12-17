package io.picsou.service;

import io.picsou.domain.Contrat;
import io.picsou.domain.EtatContrat;
import io.picsou.domain.ProduitContrat;
import io.picsou.repository.ContratRepository;
import io.picsou.repository.EtatContratRepository;

import java.sql.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratService {

	@Autowired
	ContratRepository contratRepository;

	@Autowired
	EtatContratRepository etatContratRepository;

	public Contrat getContrat(Long id) {
		return contratRepository.findOne(id);
	}

	public List<Contrat> getAllContrats() {
		return contratRepository.findAll();
	}

	public void save(Contrat contrat) {
		Float prix = 0f;
		for (ProduitContrat c : contrat.getProduitsContrat()) {
			prix += c.getPrix();
			c.setContrat(contrat);
		}
		contrat.setPrix(prix);
		contratRepository.save(contrat);
	}

	public EtatContrat getEtatContrat(Long id) {
		return etatContratRepository.findOne(id);
	}
	

	public List<Contrat> findByClientIdByEtatContratId(Long clientId,
			Long etatContratId) {
		return contratRepository.findAllByClientIdAndEtatContratId(clientId,
				etatContratId);
	}
	
	public Long getRevenuByYear(String year) {
		Long revenusResult=contratRepository.sumPriceByYear(year);
		if(revenusResult==null){
			revenusResult=0L;
		}
		return revenusResult;
	}
	
	public List<Contrat> findByEtatContratId(Long etatContratId) {
		return contratRepository.findAllByEtatContratId(etatContratId);
	}
	public List<Contrat> findAllContratAEcheance(int nbJours) {
		DateTime d = new DateTime();
		Date dmax=new java.sql.Date( d.plusDays(nbJours).getMillis() );
		Date dmin=new java.sql.Date( d.minusDays(1).getMillis() );
		return contratRepository.findByDateExecutionContratBeforeAndDateExecutionContratAfterOrDateExecutionContratIsNull(dmax,dmin);
	}
	
	public Long countByEtatContratId(long etatContratId) {
		return contratRepository.countByEtatContratId(etatContratId);
	}
	
	public Long countContratsAEcheance(int nbJours) {
		DateTime d = new DateTime();
		Date dmax=new java.sql.Date( d.plusDays(nbJours).getMillis() );
		Date dmin=new java.sql.Date( d.minusDays(1).getMillis() );
		return contratRepository.countByDateExecutionContratBeforeAndDateExecutionContratAfterOrDateExecutionContratIsNull(dmax, dmin);
	}

	public float calculPrix(Contrat contrat){
		float total=0f;
		for( ProduitContrat pc : contrat.getProduitsContrat()){
			total+=pc.getPrix();
		}
		return  total;
	}
}
