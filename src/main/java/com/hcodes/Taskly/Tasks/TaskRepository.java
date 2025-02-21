package com.hcodes.Taskly.Tasks;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public List<Task> findByCategoryName(String categoryName);
    public List<Task> findByStatus(TaskStatus status);
}
