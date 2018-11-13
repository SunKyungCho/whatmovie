package me.toybox.whatmovie_data_shipper.service.scraper;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import me.toybox.whatmovie_data_shipper.domain.Movie;
import me.toybox.whatmovie_data_shipper.service.MovieService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Component
public class KoficScraper {

    @Autowired
    MovieService movieService;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    ObjectMapper objectMapper;

    Logger logger = LoggerFactory.getLogger(KoficScraper.class);

    public List<String> getMovieCodeList() throws IOException {

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

        List<String> codeList = Collections.emptyList();
        if(jsonNode.isArray())
        {
            for (JsonNode node : jsonNode) {
                try {
                    if (isPorno(node)) {continue;} // 성인물 filter
                    String movieCd = node.get("movieCd").asText();
                    Movie movie = scrapMovieDetail(movieCd);

                    codeList.add(movieCd);
                }catch (Exception e){
                    logger.warn("");
                }
            }
        }
        return codeList;
    }

    public Movie scrapMovieDetail(String movieCode) throws IOException {

        String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json";
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
                .queryParam("key", "c3ff4ee8a4ef39229a0b67f32520229d")
                .queryParam("movieCd", movieCode);

        RestTemplate restTemplate = restTemplateBuilder.build();
        JsonNode movieDetailNode = restTemplate.getForObject(uri.toUriString(), JsonNode.class);
        JsonNode movieInfo = movieDetailNode.get("movieInfoResult").get("movieInfo");

        Movie movie = new Movie();
        movie.setMovieCode(movieCode);
        movie.setName(movieInfo.get("movieNm").asText());
        movie.setNameEn(movieInfo.get("movieNmEn").asText());

        movie.setActor(getPeopleName(movieInfo.get("actors")));
        movie.setDirector(getPeopleName(movieInfo.get("directors")));
        movie.setGenre(getGenres(movieInfo.get("genres")));
        movie.setNation(getNations(movieInfo.get("nations")));
        movie.setRating(getRatings(movieInfo.get("audits")));

        movie.setOpenDate(movieInfo.get("openDt").asText());
        movie.setShowTime(movieInfo.get("showTm").intValue());
        movie.setType(movieInfo.get("typeNm").asText());
        movie.setStatus(movieInfo.get("prdtStatNm").asText());
        movie.setProductionYear(movieInfo.get("prdtYear").asText());
        movie.setCompany(movieInfo.get("movieNmEn").asText());

        return movie;
    }

    private String getImageUrl(String movieCode) throws IOException {

        Document doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + movieCode).get();
        Elements imageDiv = doc.select(".rollList1 a");
        String onclick = imageDiv.get(0).attr("onclick");
        Pattern pattern = Pattern.compile("/.*jpg");
        Matcher matcher = pattern.matcher(onclick);
        String imageUri = matcher.group();

        return imageUri;
    }


    private String getDescription() {

        return null;
    }

    private String getNations(JsonNode nations) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>(){});
        List<JsonNode> nationJsonList = reader.readValue(nations);
        return nationJsonList.stream()
                .limit(5)
                .map(actor -> actor.get("nationNm").asText())
                .collect(Collectors.joining(","));
    }
    private String getPeopleName(JsonNode actors) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>(){});
        List<JsonNode> actorJsonList = reader.readValue(actors);
        return actorJsonList.stream()
                .limit(5)
                .map(actor -> actor.get("peopleNm").asText())
                .collect(Collectors.joining(","));
    }
    private String getGenres(JsonNode genre) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>(){});
        List<JsonNode> genreJsonList = reader.readValue(genre);
        return genreJsonList.stream()
                .map(actor -> actor.get("genreNm").asText())
                .collect(Collectors.joining(","));
    }
    private String getRatings(JsonNode rating) throws IOException {
        ObjectReader reader = objectMapper.readerFor(new TypeReference<List<JsonNode>>(){});
        List<JsonNode> ratingJsonList = reader.readValue(rating);
        return ratingJsonList.stream()
                .map(actor -> actor.get("watchGradeNm").asText())
                .collect(Collectors.joining(","));
    }

    private Boolean isPorno(JsonNode movie) {
        String genre = movie.get("genreAlt").asText();
        if(genre.equals("성인물(에로)")){
            return true;
        }
        else {
            return false;
        }
    }

}
