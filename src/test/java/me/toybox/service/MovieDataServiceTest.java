package me.toybox.service;

import me.toybox.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieDataServiceTest {

    @Autowired
    MovieDataService movieDataService;

    @Test
    public void getMovieInfoDetail() throws IOException {
        Movie movieInfo = movieDataService.getMovieInfoDetail("20124079");
        assertThat(movieInfo).isNotNull();
        assertThat(movieInfo.getName()).isEqualTo("광해, 왕이 된 남자");
    }

    @Test
    public void getMovieDataTest() throws Exception {


        movieDataService.getMovieData();
    }
}