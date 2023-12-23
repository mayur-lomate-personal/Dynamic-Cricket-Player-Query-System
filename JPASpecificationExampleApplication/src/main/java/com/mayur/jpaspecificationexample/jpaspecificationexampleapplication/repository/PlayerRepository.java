package com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.repository;

import com.mayur.jpaspecificationexample.jpaspecificationexampleapplication.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/*
The JpaSpecificationExecutor interface adds methods which will allow us to execute Specifications, for example, these:
List<T> findAll(Specification<T> spec);
Page<T> findAll(Specification<T> spec, Pageable pageable);
List<T> findAll(Specification<T> spec, Sort sort);
*/

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long>, JpaSpecificationExecutor<Player> {
}
