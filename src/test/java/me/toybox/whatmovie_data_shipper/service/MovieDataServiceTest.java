package me.toybox.whatmovie_data_shipper.service;


import me.toybox.whatmovie_data_shipper.domain.Movie;
import me.toybox.whatmovie_data_shipper.domain.MovieUpdate;
import me.toybox.whatmovie_data_shipper.repository.MovieRepository;
import me.toybox.whatmovie_data_shipper.repository.MovieUpdateRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieDataServiceTest {

    @Autowired
    MovieDataService movieDataService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieUpdateRepository movieUpdateRepository;

    @Test
    public void getMovieInfoDetail() throws IOException {
        Movie movieInfo = movieDataService.getMovieInfoDetail("20124079");
        assertThat(movieInfo).isNotNull();
        assertThat(movieInfo.getName()).isEqualTo("광해, 왕이 된 남자");
    }


    @Test
    public void getMovieInfoDetail_limit3000() throws IOException {

        List<MovieUpdate> movieUpdateList = movieUpdateRepository.findTop3000ByIsUpdate(false, PageRequest.of(0,3000));
        assertThat(movieUpdateList).isNotNull();
        assertThat(movieUpdateList.size()).isEqualTo(3000);
    }

    @Test
    public void saveMovieDataTest() throws Exception {

        movieDataService.saveMovieDetail();

        List<Movie> savedMovieList = movieRepository.findAll();
        assertThat(savedMovieList.size()).isEqualTo(3000);
    }
}