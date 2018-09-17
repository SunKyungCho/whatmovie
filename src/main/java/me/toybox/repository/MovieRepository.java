package me.toybox.repository;

import me.toybox.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByMovieCode(String movieCode);


}
