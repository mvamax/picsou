package io.picsou.config;

import io.picsou.domain.Adresse;
import io.picsou.domain.CatalogueProduit;
import io.picsou.domain.CatalogueType;
import io.picsou.domain.Client;
import io.picsou.domain.Contrat;
import io.picsou.domain.EtatContrat;
import io.picsou.domain.EtatContratEnum;
import io.picsou.domain.ProduitContrat;
import io.picsou.domain.UserInformation;
import io.picsou.repository.EtatContratRepository;
import io.picsou.service.CatalogueService;
import io.picsou.service.ClientService;
import io.picsou.service.ContratService;
import io.picsou.service.UserInformationService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FillDatabaseComponent {

	@Autowired
	EtatContratRepository etatContratRepository;

	@Autowired
	ClientService clientService;

	@Autowired
	UserInformationService userInformationService;
	
	@Autowired
	ContratService contratService;

	@Autowired
	CatalogueService catalogueService;

	private final static DateTime start = new DateTime(2015, 1, 1, 0, 0);

	public void fill() {
		UserInformation userInformation = new UserInformation();
		userInformation.setEmail("maxence@gmail.com");
		userInformation.setNom("Dondon");
		userInformation.setNomEntreprise("Mieux vaut appeler Max");
		userInformation.setPrenom("Maxence");
		userInformation.setTelephone("01 02 01 02 01");
		Adresse userAdresse = new Adresse();
		userAdresse.setChamp1("22 rue du capitaine Ferber");
		userAdresse.setChamp6("92130 Issy les moulineaux");
		userInformation.setAdresse(userAdresse);
		userInformationService.save(userInformation);
		
		EtatContrat enCours = new EtatContrat();
		enCours.setId(10L);
		enCours.setIntitule("En cours");
		etatContratRepository.save(enCours);

		EtatContrat EnAttentePaiement = new EtatContrat();
		EnAttentePaiement.setId(20L);
		EnAttentePaiement.setIntitule("En attente de paiement");
		etatContratRepository.save(EnAttentePaiement);

		EtatContrat Termine = new EtatContrat();
		Termine.setId(30L);
		Termine.setIntitule("Terminé");
		etatContratRepository.save(Termine);

		EtatContrat Annule = new EtatContrat();
		Annule.setId(90L);
		Annule.setIntitule("Annulé");
		etatContratRepository.save(Annule);

		CatalogueProduit c = new CatalogueProduit();
		c.setIntitule("Super Produit");
		c.setDescription("c'est la star des produits");
		c.setPrixReference(1000f);
		CatalogueType ct = new CatalogueType();
		ct.setType("Jardinage");
		catalogueService.saveTypeInCatalogue(ct);

		CatalogueType ct2 = new CatalogueType();
		ct2.setType("Bricolage");
		catalogueService.saveTypeInCatalogue(ct2);

		c.setCatalogueType(ct);
		catalogueService.saveProduitInCatalogue(c);

	}

	public void fillObjects(int nbContratsEnCours,
			int nbContratsEnAttentePaiement, int nbContratTermines) {
		

		Client c = new Client();
		c.setNom(UUID.randomUUID().toString().replace("-", "").substring(0, 12));
		c.setPrenom(UUID.randomUUID().toString().replace("-", "")
				.substring(0, 12));
		clientService.save(c);

		for (int i = 0; i < nbContratsEnCours; i++) {
			Contrat contrat = new Contrat();
			contrat.setClient(c);
			contrat.setDateExecutionContrat(start.plusDays(
					(int) (Math.random() * 365)).toDate());
			contrat.setEtatContrat(contratService
					.getEtatContrat(EtatContratEnum.EN_COURS.getValue()));
			contrat.setIntitule(UUID.randomUUID().toString());

			List<ProduitContrat> produitsContrat = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				ProduitContrat produitContrat = new ProduitContrat();
				produitContrat.setIntitule(UUID.randomUUID().toString());
				produitContrat.setPrix((float) (Math.random() * 1000));
				String type;
				double seuil = Math.random();
				if (seuil < 0.25) {
					type = "Bricolage";
				} else if (seuil < 0.5) {
					type = "Jardinage";
				} else if (seuil < 0.75) {
					type = "Entretien";
				} else {
					type = "Course";
				}
				produitContrat.setTypeProduit(type);
				produitsContrat.add(produitContrat);

			}
			contrat.setPrix(contratService.calculPrix(contrat));
			contrat.setProduitsContrat(produitsContrat);

			contratService.save(contrat);
			
		}
		

	}

}