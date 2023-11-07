package com.apiproyect.NotUberServer.Repository;

import com.apiproyect.NotUberServer.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<User, Long> {
}
