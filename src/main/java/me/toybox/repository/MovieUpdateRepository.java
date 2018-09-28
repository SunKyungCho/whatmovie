package me.toybox.repository;

import me.toybox.domain.MovieUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by sunkyung on 2018. 9. 28..
 */

public interface MovieUpdateRepository extends JpaRepository<MovieUpdate, String>{

}
