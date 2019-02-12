package me.toybox.whatmovie_data_shipper.service;

import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class MovieScraper {

    Logger logger = LoggerFactory.getLogger(MovieScraper.class);


    @Autowired
    KoficService koficService;

    public List<Movie> scrapMovie() throws IOException {

        List<Movie> movieList = new ArrayList<>();
        List<String> movieCodes = koficService.getMovieCodeList();
        for (String movieCode : movieCodes) {
            Movie movie = koficService.scrapMovieDetail(movieCode);
            movieList.add(movie);
        }
        return movieList;
    }
}
