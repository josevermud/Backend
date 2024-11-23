package com.casillega.llegaApi.repositories;

import com.casillega.llegaApi.dto.Response;
import com.casillega.llegaApi.entities.AppUser;

import com.casillega.llegaApi.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUserName(String userName);  //
    boolean existsByUserEmail(String userEmail);
    Optional<AppUser> findByUserEmail(String userEmail);

}


