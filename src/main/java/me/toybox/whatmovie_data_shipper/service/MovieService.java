package me.toybox.whatmovie_data_shipper.service;


import me.toybox.whatmovie_data_shipper.domain.Movie;
import me.toybox.whatmovie_data_shipper.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;


    @Transactional
    public Boolean isExist(String movieCode) {
        Movie movie = movieRepository.findByMovieCode(movieCode);
        return movie==null ? true : false;
    }
}
