package me.toybox.whatmovie_data_shipper.service;

import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Component
public class MovieScraper {

    Logger logger = LoggerFactory.getLogger(MovieScraper.class);

    @Autowired
    KoficService koficService;

    @Autowired
    MovieService movieService;

    public List<Movie> scrapMovie() throws IOException {
        List<Movie> movieList = new ArrayList<>();

        IntStream.range(0,100)
                .forEach(n -> {
                    try{
                        List<Movie> movies = koficService.fetchMovieByPage(n+"");
                        saveMovies(movies);
                    }catch (IOException e){
                        throw new RuntimeException();
                    }
                });
        return movieList;
    }
    private void saveMovies(List<Movie> movies){
        movies.forEach(movie -> movieService.save(movie));
    }
}
