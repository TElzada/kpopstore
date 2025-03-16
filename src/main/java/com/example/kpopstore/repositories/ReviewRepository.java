package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

}
