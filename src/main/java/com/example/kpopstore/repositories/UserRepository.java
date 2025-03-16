package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface UserRepository extends JpaRepository<User, UUID> {

}
