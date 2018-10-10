package me.toybox.whatmovie_data_shipper.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name="movie")
@Getter @Setter
public class Movie {

    @Id
    @GeneratedValue
    private Integer movieNo;
    private String movieCode;
    private String name;
    private String nameEn;
    private String actor;
    private String director;
    private String company;
    private String genre;
    private String nation;
    private String rating;
    private Integer showTime;
    private String status;
    private String type;
    private String openDate;
    private String productionYear;
    @CreationTimestamp
    private Timestamp regDate;
    @UpdateTimestamp
    private Timestamp updateDate;

}
