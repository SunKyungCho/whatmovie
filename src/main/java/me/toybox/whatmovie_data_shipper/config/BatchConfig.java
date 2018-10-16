package me.toybox.whatmovie_data_shipper.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.Date;

@Configuration
@EnableScheduling
public class BatchConfig {



//    @Autowired
//    JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    StepBuilderFactory stepBuilderFactory;

    @Scheduled(fixedDelay = 1000)
    public void testprintln() {
        System.out.println(new Date());
    }


    @Scheduled(fixedDelay = 5000)
    public void testprintln2() {
        System.out.println("this is diff");
    }


}
