package io.picsou.service;

import io.picsou.domain.CatalogueProduit;
import io.picsou.domain.CatalogueType;
import io.picsou.repository.CatalogueProduitRepository;
import io.picsou.repository.CatalogueTypeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatalogueService {

	
	@Autowired
	CatalogueTypeRepository catalogueTypeRepository;
	
	@Autowired
	CatalogueProduitRepository catalogueProduitRepository;
	
	public List<CatalogueType> getAllTypesInCatalogue(){
		return catalogueTypeRepository.findAll();
	}
	
	public void saveTypeInCatalogue(CatalogueType entity){
		catalogueTypeRepository.save(entity);
	}
	
	public  List<CatalogueProduit> getAllProduitsInCatalogue(){
		return catalogueProduitRepository.findAll();
	}

	public void saveProduitInCatalogue(CatalogueProduit catalogueProduit) {
		catalogueProduitRepository.save(catalogueProduit);
	}
}
