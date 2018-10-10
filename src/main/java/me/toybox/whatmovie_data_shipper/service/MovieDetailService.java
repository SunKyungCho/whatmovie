package me.toybox.whatmovie_data_shipper.service;

import me.toybox.whatmovie_data_shipper.domain.MovieUpdate;
import me.toybox.whatmovie_data_shipper.repository.MovieUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sunkyung on 06/10/2018.
 */

@Service
public class MovieDetailService {

    final static int MAXIMUM = 3000;

    @Autowired
    MovieUpdateRepository movieUpdateRepository;

    public List<String> getDetailMovieCode() {

        List<MovieUpdate> movieCodeList = movieUpdateRepository.findTop3000ByIsUpdate(false, PageRequest.of(0, MAXIMUM));
        return movieCodeList.stream()
                .map(movieUpdate -> movieUpdate.getMovieCode())
                .collect(Collectors.toList());
    }

}



