package me.toybox;

import me.toybox.data.DataService;
import me.toybox.domain.Movie;
import me.toybox.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
public class WhatMovieApplication implements CommandLineRunner {
//public class WhatMovieApplication{

	@Autowired
	DataService service;

	public static void main(String[] args) {
		SpringApplication.run(WhatMovieApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {

		service.getMovieData();
		System.out.println("End..");
		exit(0);
	}
}
