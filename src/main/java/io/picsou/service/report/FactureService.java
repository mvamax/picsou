package io.picsou.service.report;

import io.picsou.service.ClientService;
import io.picsou.service.ContratService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FactureService {

	@Autowired
	ClientService clientService;
	
	@Autowired
	ContratService contratService;
	
	public Boolean verifieContratClientAppartenance( Long clientId, Long contratId){
		return clientService.verifieAppartenanceClientContrat(clientId, contratId);
	}
	
	
	
}
