package org.test.api.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.test.api.model.Solution;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, UUID> {

	List<Solution> findBySuccessOrderByName(boolean success);
    
}
