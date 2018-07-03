package me.toybox.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.toybox.domain.Movie;
import me.toybox.dto.MovieParam;
import me.toybox.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sunkyung on 2018. 4. 17..
 */

@Service
public class DataService {

    @Autowired
    MovieRepository movieRepository;


    public void getMovieData(){

        ObjectMapper mapper = new ObjectMapper();

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", "tokenl")
                .queryParam("prdtStartYear", "1990")
                .queryParam("prdtEndYear", "1999")
                .queryParam("itemPerPage", "50000");

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(uri.toUriString(), String.class);

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
        JsonArray jsonArray = jsonObject.get("movieListResult").getAsJsonObject().get("movieList").getAsJsonArray();
        Type listType = new TypeToken<List<MovieParam>>(){}.getType();
        List<MovieParam> movieParamList = gson.fromJson(jsonArray.toString(), listType);

        for (MovieParam movieParam : movieParamList) {
            Movie movie = new Movie(movieParam);
            try{
                movieRepository.save(movie);
            }catch (Exception e){
                System.out.println("Error!");
            }
        }
    }
}
