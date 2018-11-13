package me.toybox.whatmovie_data_shipper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MovieInfoScrapingTest {

    @Test
    public void getMovieImageInfoTest() throws IOException {

        String movieCode = "20124079"; //광해
        Document doc = Jsoup.connect("http://www.kobis.or.kr/kobis/business/mast/mvie/searchMovieDtl.do?code=" + movieCode).get();

        Elements imageDiv = doc.select(".rollList1 a");
        String onclick = imageDiv.get(0).attr("onclick");

        Pattern pattern = Pattern.compile("/.*jpg");
        Matcher matcher = pattern.matcher(onclick);

        assertThat(matcher.find()).isEqualTo(true);

        String imageUri = matcher.group();
        assertThat(imageUri).isEqualTo("/common/mast/movie/2012/12/1d40ab8330574250a6f09c2cb3f6a877.jpg");

        System.out.println(imageUri);

    }
}
