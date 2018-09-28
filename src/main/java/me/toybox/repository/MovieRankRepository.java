package me.toybox.repository;


import me.toybox.domain.MovieRank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRankRepository extends JpaRepository<MovieRank, String> {

}
