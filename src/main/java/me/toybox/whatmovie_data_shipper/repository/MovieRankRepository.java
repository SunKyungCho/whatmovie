package me.toybox.whatmovie_data_shipper.repository;


import me.toybox.whatmovie_data_shipper.domain.MovieRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRankRepository extends JpaRepository<MovieRank, String> {

}
