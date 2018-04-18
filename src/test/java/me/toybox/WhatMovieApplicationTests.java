package me.toybox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.toybox.data.DataService;
import me.toybox.domain.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Collection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhatMovieApplicationTests {

	@Autowired
	DataService dataService;

	@Test
	public void contextLoads() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
			.queryParam("key", "");


		RestTemplate restTemplate = new RestTemplate();
//		HashMap<String, Object> response = restTemplate.getForObject(uri.toUriString(), HashMap.class);
	 	String response = restTemplate.getForObject(uri.toUriString(), String.class);

		JsonNode node = mapper.readTree(response).findValue("movieList");

		Collection<Movie> readValue = mapper.readValue(node.toString(), new TypeReference<Collection<Movie>>(){});

		System.out.println(node);

//		HashMap<String, Object> movieListResult = (HashMap<String, Object>) response.get("movieListResult");
//		HashMap<String, String> movieList = (HashMap<String, String>) movieListResult.get("movieList");

//		for (String str : movieList.values()) {
//
//			Movie movie = objectMapper.readValue(str, Movie.class);
//			System.out.println(movie);



//		Map<String, String> movieListResult = (Map<String, String>) response.get("movieListResult");
//
//		response.entrySet().stream()
//			.filter(x -> x.getKey().equals("movieListResult"));

//		List<Movie> movieList = new ArrayList<>();
//
//		response.get("movieListResult");

//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.readTree(response);

//		Map<String, String> pram = new HashMap<>();
//
//		String result = restTemplate.getForObject(url, String.class, pram);




	}
}
