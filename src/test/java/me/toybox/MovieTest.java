package me.toybox;


import me.toybox.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void DatabaseConnectionTest() {

        me.toybox.domain.Movie movie = new me.toybox.domain.Movie();
        movie.setMovieCd("123");
        movie.setMovieNm("Test");
        me.toybox.domain.Movie returnMovie = movieRepository.save(movie);

        me.toybox.domain.Movie movie2 = new me.toybox.domain.Movie();
        movie.setMovieCd("123");
        movie.setMovieNm("Test");
        me.toybox.domain.Movie returnMovie2 = movieRepository.save(movie2);

        assertThat(returnMovie).isNotNull();

    }
}
