package com.dassonville.api.repository;


import com.dassonville.api.model.AttemptResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptResponseRepository extends JpaRepository<AttemptResponse, Long> {

}
