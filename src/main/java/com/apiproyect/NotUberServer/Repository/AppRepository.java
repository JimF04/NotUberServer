package com.apiproyect.NotUberServer.Repository;

import com.apiproyect.NotUberServer.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<Task, Long> {
}
