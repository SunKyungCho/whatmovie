package me.toybox;


import me.toybox.domain.MovieRank;
import me.toybox.repository.MovieRankRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class MovieRankTest {

    @Autowired
    MovieRankRepository movieRankRepository;

    @Test
    public void createMovie() {

        MovieRank movieRank = new MovieRank();
        movieRank.setMovieCd("test");
        movieRankRepository.save(movieRank);

        List<MovieRank> list = movieRankRepository.findAll();
        assertThat(list).isEqualTo(1);
    }
}
