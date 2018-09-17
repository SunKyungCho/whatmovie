package me.toybox.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.toybox.domain.Movie;
import me.toybox.repository.MovieRankRepository;
import me.toybox.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sunkyung on 2018. 4. 17..
 */

@Service
public class MovieDataService {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieRankRepository movieRankRepository;
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    ObjectMapper objectMapper;


    public void getMovieData() throws IOException {

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", "c3ff4ee8a4ef39229a0b67f32520229d")
                .queryParam("openStartDt", "0000")
                .queryParam("openEndDt", "2019")
                .queryParam("itemPerPage", "50000");

        RestTemplate restTemplate = restTemplateBuilder.build();
        String response = restTemplate.getForObject(uri.toUriString(), String.class);

        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode jsonNode = rootNode.get("movieListResult").get("movieList");

        if(jsonNode.isArray())
        {
            for (JsonNode node : jsonNode) {
                String movieCd = node.get("movieCd").toString();
                getMovieInfoDetail(movieCd);
            }
        }
    }

    public Movie getMovieInfoDetail(String movieCode) {

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", "c3ff4ee8a4ef39229a0b67f32520229d")
                .queryParam("movieCd", movieCode);

        RestTemplate restTemplate = restTemplateBuilder.build();
        JsonNode movieDetailNode = restTemplate.getForObject(uri.toUriString(), JsonNode.class);
        JsonNode movieInfo = movieDetailNode.get("movieListResult").get("movieInfo");

        Movie movie = new Movie();
        movie.setName(movieInfo.get("movieNm").toString());
        movie.setNameEn(movieInfo.get("movieNmEn").toString());

        movie.setActor(getPeopleName(movieInfo.get("actors")));
        movie.setDirector(getPeopleName(movieInfo.get("directors")));
        movie.setGenre(getGenres(movieInfo.get("genres")));

        movie.setRating(movieInfo.get("").toString()); //나이
        movie.setOpenDate(movieInfo.get("openDt").toString());
        movie.setType(movieInfo.get("typeNm").toString());
        movie.setStatus(movieInfo.get("prdtStatNm").toString());
        movie.setNation(movieInfo.get("movieNmEn").toString());
        movie.setProductionYear(movieInfo.get("movieNmEn").toString());
        movie.setCompany(movieInfo.get("movieNmEn").toString());

        return movie;
    }

    private String getPeopleName(JsonNode actors) {
        List<JsonNode> actorJsonList = (List<JsonNode>) actors.iterator();
        return actorJsonList.stream()
                .limit(5)
                .map(actor -> actor.get("peopleNm").toString())
                .collect(Collectors.joining(","));
    }

    private String getGenres(JsonNode actors) {
        List<JsonNode> actorJsonList = (List<JsonNode>) actors.iterator();
        return actorJsonList.stream()
                .map(actor -> actor.get("genreNm").toString())
                .collect(Collectors.joining(","));
    }

}
