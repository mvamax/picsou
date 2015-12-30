package io.picsou.repository;

import io.picsou.domain.event.PersistentEvent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersistentEventRepository extends JpaRepository<PersistentEvent,Long>{

}
