package me.toybox.domain;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Movie")
public class Movie {

    @Id
    private String movieCd;
    @Column
    private String movieNm;
    @Column
    private String movieNmEn;
    @Column
    private String prdtYear;
    @Column
    private String openDt;
    @Column
    private String typeNm;
    @Column
    private String prdtStatNm;
    @Column
    private String nationAlt;
    @Column
    private String genreAlt;
    @Column
    private String repNationNm;
    @Column
    private String repGenreNm;
    @Column
    private String directors;
    @Column
    private String peopleNm;
    @Column
    private String companys;
    @Column
    private String companyCd;
    @Column
    private String companyNm;
}
