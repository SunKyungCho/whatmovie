package me.toybox.whatmovie_data_shipper.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import me.toybox.whatmovie_data_shipper.domain.Movie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class KoficService {

    private final static String START_YEAR = "0000";
    private final static String MAX_YEAR = "9999";
    private final static String MOVIE_COUNT = "100";
    private final static String MOVIE_LIST_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
    private final static String MOVIE_DETAIL_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";

    private ObjectMapper objectMapper;
    private RestTemplate restTemplate;

    public KoficService() {
        this.objectMapper = new ObjectMapper();
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @Value("${kofic.key}")
    String myKey;

    public List<String> fetchMovieByPage(int page) {

        JsonNode response = restTemplate.getForObject(getMovieListUrlByPage(page), JsonNode.class);
        JsonNode jsonNode = response.get("movieListResult").get("movieList");

        List<String> movieCodes = new ArrayList<>();
        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                movieCodes.add(node.get("movieCd").asText());
            }
        }
        return movieCodes;
    }

    public Movie fetchMovieDetail(String movieCode) throws IOException {

        JsonNode movieDetailNode = restTemplate.getForObject(getMovieDetailUrl(movieCode), JsonNode.class);
        JsonNode movieInfo = movieDetailNode.get("movieInfoResult").get("movieInfo");

        Movie movie = Movie.builder()
                .movieCode(movieCode)
                .name(movieInfo.get("movieNm").asText())
                .nameEn(movieInfo.get("movieNmEn").asText())
                .actors(getPeopleName(movieInfo.get("actors")))
                .director(getPeopleName(movieInfo.get("directors")))
                .genre(getGenres(movieInfo.get("genres")))
                .nation(getNations(movieInfo.get("nations")))
                .rating(getRatings(movieInfo.get("audits")))
                .openDate(movieInfo.get("openDt").asText())
                .showTime(movieInfo.get("showTm").intValue())
                .type(movieInfo.get("typeNm").asText())
                .productionYear(movieInfo.get("prdtYear").asText())
                .status(movieInfo.get("prdtStatNm").asText())
                .build();
        return movie;
    }

    private String getMovieDetailUrl(String movieCode) {
        return UriComponentsBuilder.fromUriString(MOVIE_DETAIL_URL)
                .queryParam("key", myKey)
                .queryParam("movieCd", movieCode)
                .toUriString();
    }

    private String getMovieListUrlByPage(int page) {
        return UriComponentsBuilder.fromUriString(MOVIE_LIST_URL)
                .queryParam("key", myKey)
                .queryParam("openStartDt", START_YEAR)
                .queryParam("openEndDt", MAX_YEAR)
                .queryParam("itemPerPage", MOVIE_COUNT)
                .queryParam("curPage", page)
                .toUriString();
    }

    private String getImageUrl(String movieCode) throws IOException {
        Document doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + movieCode).get();
        Elements imageDiv = doc.select(".rollList1 a");
        String onclick = imageDiv.get(0).attr("onclick");
        Matcher matcher = Pattern.compile("/.*jpg").matcher(onclick);
        return matcher.group();
    }

    private String getDescription() {
        return null;
    }

    private String getNations(JsonNode nations) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>() {
        });
        List<JsonNode> nationJsonList = reader.readValue(nations);
        return nationJsonList.stream()
                .limit(5)
                .map(actor -> actor.get("nationNm").asText())
                .collect(Collectors.joining(","));
    }

    private String getPeopleName(JsonNode actors) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>() {
        });
        List<JsonNode> actorJsonList = reader.readValue(actors);
        return actorJsonList.stream()
                .limit(5)
                .map(actor -> actor.get("peopleNm").asText())
                .collect(Collectors.joining(","));
    }

    private String getGenres(JsonNode genre) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>() {
        });
        List<JsonNode> genreJsonList = reader.readValue(genre);
        return genreJsonList.stream()
                .map(actor -> actor.get("genreNm").asText())
                .collect(Collectors.joining(","));
    }

    private String getRatings(JsonNode rating) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>() {
        });
        List<JsonNode> ratingJsonList = reader.readValue(rating);
        return ratingJsonList.stream()
                .map(actor -> actor.get("watchGradeNm").asText())
                .collect(Collectors.joining(","));
    }
}
