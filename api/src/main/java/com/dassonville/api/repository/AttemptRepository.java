package com.dassonville.api.repository;

import com.dassonville.api.model.Attempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {
}
