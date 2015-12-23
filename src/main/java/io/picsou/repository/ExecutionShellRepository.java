package io.picsou.repository;

import io.picsou.domain.ExecutionShell;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionShellRepository extends JpaRepository<ExecutionShell,Long>{

}