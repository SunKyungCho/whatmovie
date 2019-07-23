package me.toybox.whatmovie_data_shipper.service;

import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KoficServiceTest {

    private KoficService koficService;

    @Before
    public void setUp() throws Exception {
        koficService = new KoficService();
    }

    @Test
    public void getMovieCodeList() {
        List<String> codeList = koficService.fetchMovieByPage(1);
        assertThat(codeList.size()).isEqualTo(100);
    }

    @Test
    public void getMovieDetailTest() throws IOException {
        Movie movie = koficService.fetchMovieDetail("20124079");
        assertThat(movie.getName()).isEqualTo("광해, 왕이 된 남자");
    }
}