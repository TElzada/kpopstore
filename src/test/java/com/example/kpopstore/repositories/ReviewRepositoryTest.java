package com.example.kpopstore.repositories;

import com.example.kpopstore.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Optional;
import java.math.BigDecimal;
import com.example.kpopstore.entities.User.Role;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    void testSaveAndFindReview() {
        // Создаем пользователя
        User user = new User("impala67", "dean.winchesterdoe@gmail.com", "password123", Role.USER);
        user = userRepository.save(user);

        // Создаем альбом
        Album album = new Album("Wings", "BTS", LocalDate.of(2016, 10, 10),
                BigDecimal.valueOf(20.99), 100, "2nd FULL-LENGTH ALBUM", "cover1.jpg");
        album = albumRepository.save(album);

        // Создаем обзор
        Review review = new Review("Amazing album!", 5, LocalDateTime.now(), album, user);
        review = reviewRepository.save(review);

        Optional<Review> foundReview = reviewRepository.findById(review.getId());

        assertThat(foundReview).isPresent();
        assertThat(foundReview.get().getRating()).isEqualTo(5);
    }
}

