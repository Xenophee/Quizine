package com.dassonville.quizine.repository;

import com.dassonville.quizine.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByLabel(String label);

    Optional<Status> findByLabel(String label);
}
