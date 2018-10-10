package me.toybox.whatmovie_data_shipper.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name="movie_update")
@Getter @Setter
public class MovieUpdate {

    @Id
    @GeneratedValue
    private Integer movieNo;
    private String movieCode;
    private Boolean isUpdate = false;
    @UpdateTimestamp
    private Timestamp updateDate;

}