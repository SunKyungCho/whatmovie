package me.toybox.service;


import me.toybox.domain.Movie;
import me.toybox.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
