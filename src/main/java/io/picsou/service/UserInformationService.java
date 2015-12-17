package io.picsou.service;

import io.picsou.domain.UserInformation;
import io.picsou.repository.UserInformationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationService {

	@Autowired
	UserInformationRepository userInformationRepository;

	public void save(UserInformation userInformation) {
		userInformation.setId(1L);
		userInformationRepository.save(userInformation);
	}

	public UserInformation findById(Long id) {
		UserInformation userInfos = userInformationRepository.findOne(id);
		if (userInfos == null) {
			return new UserInformation();
		} else {
			return userInfos;

		}

	}
}
