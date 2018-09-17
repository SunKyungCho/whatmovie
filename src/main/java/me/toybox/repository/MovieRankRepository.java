package me.toybox.repository;


import me.toybox.domain.MovieRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRankRepository extends JpaRepository<MovieRank, String> {

}
