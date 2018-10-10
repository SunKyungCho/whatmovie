package me.toybox.whatmovie_data_shipper.repository;

import me.toybox.whatmovie_data_shipper.domain.MovieUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sunkyung on 2018. 9. 28..
 */

public interface MovieUpdateRepository extends JpaRepository<MovieUpdate, String> {


    List<MovieUpdate> findTop3000ByIsUpdate(boolean b, Pageable pageable);
}
