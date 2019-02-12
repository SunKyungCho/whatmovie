package me.toybox.whatmovie_data_shipper.service;


import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScrapService {

    @Autowired
    KoficService koficService;

    @Autowired
    MovieService movieService;


    // 전체 리스트를 가지고 온다.
    public void getMovieData() throws IOException {

        List<String> movieCodeList = koficService.getMovieCodeList();
        for (String movieCode : movieCodeList) {



        }


    }

    public void detailData() {


    }
}
