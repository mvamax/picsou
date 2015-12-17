package io.picsou.service;

import io.picsou.domain.Charge;
import io.picsou.repository.ChargeRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

	@Autowired
	ChargeRepository chargeRepository;
	
	public List<Charge> findAll(){
		return chargeRepository.findAll();
	}
	
	public void save(Charge charge){
		chargeRepository.save(charge);
	}
}
