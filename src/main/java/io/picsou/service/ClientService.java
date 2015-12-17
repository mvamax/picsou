package io.picsou.service;

import io.picsou.domain.Client;
import io.picsou.domain.Contrat;
import io.picsou.repository.ClientRepository;
import io.picsou.util.DataTable;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	@Autowired
	ClientRepository clientRepository;

	public Client getClient(Long id) {
		return clientRepository.findOne(id);
	}

	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	public void save(Client client) {
		clientRepository.save(client);
	}

	public boolean verifieAppartenanceClientContrat(Long clientId,Long contratId){
		return clientRepository.verifieAppartenanceClientContrat(clientId,contratId) > 0;
	}
	public DataTable<Client> saveDataTable(Client client) {
		clientRepository.save(client);
		List<Client> clientList = new ArrayList<Client>();
		clientList.add(client);
		DataTable<Client> dclient = new DataTable<Client>();
		dclient.setData(clientList);
		return dclient;
	};
}
