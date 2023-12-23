import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testEnviroment {
    public static void main(String[] args) throws IOException {

/*
        Document tracker = Jsoup.connect("https://nj.milesplit.com/meets/505896-sjtca-winter-meet-14-2023/results/874245?type=raw").get();
        Element meetResultsBody = tracker.getElementById("meetResultsBody");
        Elements results = meetResultsBody.getElementsByTag("pre");


 */
        String resultLine = "  M Rahway HS            4:50.00    4:50.64  20";

        if(resultLine.matches("^(\\W+?|\\d+?)?.*")){

            Pattern pattern = Pattern.compile("([a-zA-Z]).*");
            Matcher matcher = pattern.matcher(resultLine);  //<-- First Letter Found

            if(matcher.find()) {

                String newstring = resultLine.substring(resultLine.indexOf(matcher.group(1)));

            }




        }



    }
}
