package me.toybox.domain;

import lombok.Getter;
import lombok.Setter;
import me.toybox.dto.Company;
import me.toybox.dto.Director;
import me.toybox.dto.MovieParam;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.stream.Collectors;

@Entity
//@Table(name = "movie2000")
@Getter @Setter
public class Movie {

    @Id
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String prdtYear;
    private String openDt;
    private String typeNm;
    private String prdtStatNm;
    private String nationAlt;
    private String genreAlt;
    private String repNationNm;
    private String repGenreNm;
    private String directors;
    private String companys;

    public Movie() {
    }

    public Movie(MovieParam movieParam) {
        this.movieCd = movieParam.getMovieCd();
        this.movieNm = movieParam.getMovieNm();
        this.movieNmEn = movieParam.getMovieNmEn();
        this.prdtYear = movieParam.getPrdtYear();
        this.openDt = movieParam.getOpenDt();
        this.typeNm = movieParam.getTypeNm();
        this.prdtStatNm = movieParam.getPrdtStatNm();
        this.nationAlt = movieParam.getNationAlt();
        this.genreAlt = movieParam.getGenreAlt();
        this.repNationNm = movieParam.getRepNationNm();
        this.repGenreNm = movieParam.getRepGenreNm();

        this.directors = movieParam.getDirectors().stream()
                .map(Director::getPeopleNm)
                .limit(10)
                .collect(Collectors.joining(", "));

        this.companys = movieParam.getCompanys().stream()
                .map(Company::getCompanyNm)
                .limit(10)
                .collect(Collectors.joining(", "));
    }

}
