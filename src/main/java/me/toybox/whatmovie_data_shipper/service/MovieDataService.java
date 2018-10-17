package me.toybox.whatmovie_data_shipper.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import me.toybox.whatmovie_data_shipper.domain.Movie;
import me.toybox.whatmovie_data_shipper.domain.MovieUpdate;
import me.toybox.whatmovie_data_shipper.repository.MovieRankRepository;
import me.toybox.whatmovie_data_shipper.repository.MovieRepository;
import me.toybox.whatmovie_data_shipper.repository.MovieUpdateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.PageRequest;
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

    private Logger logger = LoggerFactory.getLogger(MovieDataService.class);

    @Autowired
    MovieDetailService movieDetailService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieRankRepository movieRankRepository;

    @Autowired
    MovieUpdateRepository movieUpdateRepository;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    ObjectMapper objectMapper;

    public void getMovieData() throws Exception{

        logger.info("get Movie Data. ");
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
                try {
                    if (isPorno(node)) {continue;} // 성인물 filter
                    MovieUpdate movieUpdate = new MovieUpdate();
                    String movieCd = node.get("movieCd").asText();
                    movieUpdate.setMovieCode(movieCd);
                    movieUpdate.setIsUpdate(false);
                    movieUpdateRepository.save(movieUpdate);
                }catch (Exception e){
                    logger.warn("");
                }
            }
        }
    }

    public void saveMovieDetail(){

        logger.info("Started update movie");
        List<MovieUpdate> movieCodeList = movieUpdateRepository.findTop3000ByIsUpdate(false, PageRequest.of(0,3000));
        if(movieCodeList.size() == 0) {
            logger.info("All movie was updated...");
            return;
        }
        logger.info("Need to update the movies are..." + movieCodeList.size());
        for (MovieUpdate movieUpdate : movieCodeList) {
            String movieCode = movieUpdate.getMovieCode();
            Movie movie= null;
            try {
                movie = getMovieInfoDetail(movieCode);
            } catch (IOException e) {
                logger.warn("It can't get movie info. code : " + movieCode);
                e.printStackTrace();
            }
            movieRepository.save(movie);

            movieUpdate.setIsUpdate(true);
            movieUpdateRepository.save(movieUpdate);
        }

        logger.info("Done... MovieDetail update");
    }

    public Movie getMovieInfoDetail(String movieCode) throws IOException {

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
