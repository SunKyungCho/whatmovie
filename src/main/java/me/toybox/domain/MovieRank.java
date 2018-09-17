package me.toybox.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class MovieRank {

    @Id
    @GeneratedValue
    private int id;
    private String boxofficeType; //박스오피스 종류
    private String showRange; // 상영기간
    private String yearWeekTime; // 해당 연도와 주차
    private String rnum; //순번
    private String rank; //해당 일자 순위
    private String rankInten; // 전일 대비 순위의 증감ㅋ분
    private String rankOldAndNew; //"OLD" : 기존 , "NEW" : 신규
    private String movieCd;  // 영화코드
    private String movieNm; //영화 국문
    private String openDt;  // 개봉일
    private String salesAmt; // 해당일 매출액
    private String salesShare; // 상영작의 매출총액 대비 영화 매출 비율
    private String salesInten; // 전일 대비 매출액 증감분
    private String salesChange; // 전일 대비 매출액 증감 비율
    private String salesAcc; // 누적 매출액
    private int audiCnt; // 관객수
    private String audiInten; //관객수 증감분
    private String audiChange; //관객수 증감 비율
    private int audiAcc; // 누적 관객수
    private int scrnCnt; // 스크린 수
    private int showCnt; // 상영된 횟수

}
