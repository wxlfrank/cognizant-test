package org.test.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.test.api.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}