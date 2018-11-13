package me.toybox.whatmovie_data_shipper.service;


import me.toybox.whatmovie_data_shipper.domain.Movie;
import me.toybox.whatmovie_data_shipper.service.scraper.MovieScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MovieScrapService {


    @Autowired
    MovieScraper movieScraper;

    @Autowired
    MovieService movieService;

    public void scrapMovieData() throws IOException {

        List<Movie> movies = movieScraper.scrapMovie();
        for (Movie movie : movies) {
            movieService.save(movie);
        }
    }
}
