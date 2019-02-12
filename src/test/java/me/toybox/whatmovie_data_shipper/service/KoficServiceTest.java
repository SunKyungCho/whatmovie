package me.toybox.whatmovie_data_shipper.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.asm.Advice;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class KoficServiceTest {

    @Autowired
    private KoficService koficService;

    @Test
    public void getMovieCodeList() throws IOException {
        List<String> codeList = koficService.getMovieCodeList();
        assertThat(codeList.size()).isNotEqualTo(Collections.EMPTY_LIST);
    }
}