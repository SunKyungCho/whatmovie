package me.toybox.whatmovie_data_shipper.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class MovieServiceTest {

    @Autowired
    MovieService movieService;


    @Test
    public void isExist() {

        Boolean isExist = movieService.isExist("test");
        assertThat(isExist).isEqualTo(false);

    }
}