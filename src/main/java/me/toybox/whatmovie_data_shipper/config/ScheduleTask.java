package me.toybox.whatmovie_data_shipper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleTask {

    @Autowired
    MovieDataService movieDataService;

//    @Scheduled(cron = "5 1 1 * * *")
//    public void totalMovieDataUpdate() {

//        try {
//            movieDataService.getMovieData();
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }

//    }

    @Scheduled(cron = "15 1 * * * *")
    public void getMovieDetailInfo() {

        try {
            movieDataService.saveMovieDetail();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


}
