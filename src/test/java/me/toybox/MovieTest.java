package me.toybox;


import me.toybox.domain.Movie;
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
    public void MovieRepositoryTest() {

        assertThat(movieRepository).isNotNull();
//        Movie movie = new Movie();
//        movieRepository.save(movie);
    }


}
