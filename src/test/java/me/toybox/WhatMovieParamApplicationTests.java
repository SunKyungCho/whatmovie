package me.toybox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.toybox.data.DataService;
import me.toybox.domain.Movie;
import me.toybox.dto.MovieParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WhatMovieParamApplicationTests {

	@Autowired
	DataService dataService;

	@Test
	public void contextLoads() throws IOException {

		ObjectMapper mapper = new ObjectMapper();

		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json";
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(url)
				.queryParam("key", "c3ff4ee8a4ef39229a0b67f32520229d");
//				.queryParam("itemPerPage", "100000");


		RestTemplate restTemplate = new RestTemplate();
//		HashMap<String, Object> response = restTemplate.getForObject(uri.toUriString(), HashMap.class);
		String response = restTemplate.getForObject(uri.toUriString(), String.class);

		Gson gson = new Gson();
		JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
		JsonArray jsonArray = jsonObject.get("movieListResult").getAsJsonObject().get("movieList").getAsJsonArray();
		Type listType = new TypeToken<List<MovieParam>>(){}.getType();
		List<MovieParam> movieParmaList = gson.fromJson(jsonArray.toString(), listType);

		System.out.println();



//        System.out.println(movieListResult);
//		JsonNode node = mapper.readTree(response).findValue("movieList").get(1);

//		MovieParam readValue = mapper.treeToValue(node, MovieParam.class);
//        Collection<MovieParam> readValue = mapper.readValue(node.toString(), new TypeReference<List<MovieParam>>(){});

//		System.out.println(node);

//		HashMap<String, Object> movieListResult = (HashMap<String, Object>) response.get("movieListResult");
//		HashMap<String, String> movieList = (HashMap<String, String>) movieListResult.get("movieList");

//		for (String str : movieList.values()) {
//
//			MovieParam movie = objectMapper.readValue(str, MovieParam.class);
//			System.out.println(movie);



//		Map<String, String> movieListResult = (Map<String, String>) response.get("movieListResult");
//
//		response.entrySet().stream()
//			.filter(x -> x.getKey().equals("movieListResult"));

//		List<MovieParam> movieList = new ArrayList<>();
//
//		response.get("movieListResult");

//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.readTree(response);

//		Map<String, String> pram = new HashMap<>();
//
//		String result = restTemplate.getForObject(url, String.class, pram);




	}
}
