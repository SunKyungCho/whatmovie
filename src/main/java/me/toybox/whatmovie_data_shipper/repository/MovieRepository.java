package me.toybox.whatmovie_data_shipper.repository;

import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {

    Movie findByMovieCode(String movieCode);


}
