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
        String resultLine = "https://ny.milesplit.com/meets/371240-coach-glynn-holiday-carnival-2020/results/683003?type=raw";
        //https://nj\.milesplit\.com/meets/505896-sjtca-winter-meet-14-2023/results/874245\?type=raw

        Pattern p = Pattern.compile("(^((https://?([a-z]{0,2}))|[a-z]{0,2})\\.)??milesplit.*type=raw");
        Matcher m = p.matcher(resultLine);
        System.out.println(m.matches());

        if(m.matches()){

            System.out.println(m.group(1));
            System.out.println(m.group(2));
            System.out.println(m.group(4));
            System.out.println(m.group());




        }



    }
}
