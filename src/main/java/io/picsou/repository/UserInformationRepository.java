package io.picsou.repository;

import io.picsou.domain.UserInformation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation,Long> {

}
