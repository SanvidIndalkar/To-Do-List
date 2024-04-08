package com.todo.repositories;

import com.todo.entities.Tasks;
import com.todo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    @Modifying
    @Query("update Tasks t set t.taskStatus = :status where t.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") Status status);
}
