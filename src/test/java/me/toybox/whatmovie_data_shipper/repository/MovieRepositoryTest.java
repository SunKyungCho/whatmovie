package me.toybox.whatmovie_data_shipper.repository;


import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class MovieRepositoryTest {

    @Autowired
    MovieRepository movieRepository;

    @Test
    public void findByMovieCode() {

        Movie movie = movieRepository.findByMovieCode("");
        assertThat(movie).isNull();

    }
}