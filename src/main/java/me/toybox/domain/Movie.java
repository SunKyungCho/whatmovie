package me.toybox.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by sunkyung on 2018. 4. 17..
 */
@Data
@Getter
@Setter
public class Movie {

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
    private String peopleNm;
    private String companys;
    private String companyCd;
    private String companyNm;

}
