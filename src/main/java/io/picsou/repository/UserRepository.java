package io.picsou.repository;

import io.picsou.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findOneByUsername(String username);


}