package me.toybox;

import me.toybox.service.MovieDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by sunkyung on 2018. 8. 30..
 */
@Component
public class MovieDataRunner implements ApplicationRunner {




    @Autowired
    private MovieDataService movieDataService;

    @Override
    public void run(ApplicationArguments args) {

        System.out.println("Hello wolrd");
//        movieDataService.getMovieData();

    }
}
