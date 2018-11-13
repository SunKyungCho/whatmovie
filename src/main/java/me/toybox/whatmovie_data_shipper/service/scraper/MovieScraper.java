package me.toybox.whatmovie_data_shipper.service.scraper;

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
    KoficScraper koficScraper;

    public List<Movie> scrapMovie() throws IOException {

        List<Movie> movieList = new ArrayList<>();
        List<String> movieCodes = koficScraper.getMovieCodeList();
        for (String movieCode : movieCodes) {
            Movie movie = koficScraper.scrapMovieDetail(movieCode);
            movieList.add(movie);
        }
        return movieList;
    }
}
