package me.toybox.whatmovie_data_shipper.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by sunkyung on 2018. 4. 17..
 */
@Getter
@Setter
public class MovieParam {

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
    private List<Director> directors;
    private List<Company> companys;
}

