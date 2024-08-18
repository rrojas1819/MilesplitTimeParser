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


        String school = "Rahway";
        Document doc = Jsoup.connect("https://ny.milesplit.com/meets/371240-coach-glynn-holiday-carnival-2020/results/683003?type=raw").get();
        Element meetResultsBody = doc.getElementById("subheader");
        Elements results = meetResultsBody.getElementsByClass("meetName");
        String fullResults = results.first().text();
        System.out.println(fullResults);




        //Test case 1:  https://nj.milesplit.com/meets/452060-the-varsity-classic-2023/results/873324?type=raw                                             PASSED.              initial (by commas)
        //Test case 2:  https://nj.milesplit.com/meets/548099-trials-of-miles-xc-opening-night-presented-by-new-balance-2023/results/955421?type=raw       PASSED.           \t
        //Test case 3:  https://nj.milesplit.com/meets/511411-njsiaa-sectional-championships-north-2-group-2and3-2023/results/873249?type=raw              PASSED.            No commas
        //Test case 4:  https://nj.milesplit.com/meets/467245-rahway-rising-stars-invitational-1-2022/results/800004                                       PASSED.            Empty space for teams
        //Test case 5:  https://nj.milesplit.com/meets/530391-magee-memorial-class-meet-2023/results/963795?type=raw                                       PASSED.        worked
        //Test case 6:  https://ny.milesplit.com/meets/371240-coach-glynn-holiday-carnival-2020/results/683003?type=raw                                    PASSED.        ""
        //Test case 7:  https://nj.milesplit.com/meets/420556-blue-devil-classic-2021/results/740378?type=raw                                              PASSED.        2 pres, (failing because it is not reading the intial first time)
        //Test case 8:  https://nj.milesplit.com/meets/483051-union-county-championship-relays-2023/results/924436?type=raw                                PASSED.          %
        //Test case 9:  https://nj.milesplit.com/meets/509578-garden-state-showcase-2-2022/results                                                         PASSED.         Single character
        //Test case 10: https://nj.milesplit.com/meets/543091-union-county-jv-championship-2023/results/928511?type=raw                                    PASSED.          -----WE GOOD-----(so far)
        //Test case 11: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw                                   PASSED.           "Was reading relay names"
        //Test case 12: https://nj.milesplit.com/meets/548750-union-county-conference-championships-2023/results/933203?type=raw                           PASSED.
        //Test case 13: https://nj.milesplit.com/meets/505896-sjtca-winter-meet-14-2023/results/874245?type=raw                                            PASSED.
        //Test case 14: https://nj.milesplit.com/meets/516266-holmdel-twilight-series-royal-rumble-2023/results/936820?type=raw                            PASSED.
        //Test case 15: https://nj.milesplit.com/meets/501587-the-egg-club-invitational-2023/results/871160?type=raw                                       PASSED.
        //Test case 16: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw                                   DUPE
        //Test case 17: https://ny.milesplit.com/meets/535855-track-night-nyc-2023/results/939429?type=raw                                                 PASSED.
        //Test case 18: https://ny.milesplit.com/meets/497859-the-armory-frosh-novice-meet-2023/results                                                    PASSED.    "ND" adde
        //Test case 19: https://nj.milesplit.com/meets/461526-blue-devil-classic-2022/results/806813                                                       PASSED.
        //Test case 20: https://ny.milesplit.com/meets/327901-armory-coaches-hall-of-fame-invitational-2019/results                                        PASSED.
        //Test case 21: https://ny.milesplit.com/meets/327891-spike-shoe-holiday-festival-2019/results/622770?type=raw                                     PASSED.
        //Test case 22: https://nj.milesplit.com/meets/335142-mid-winter-classic-2019/results/624531?type=raw                                              PASSED.
        //Test case 23: https://nj.milesplit.com/meets/334342-lavino-relays-2019/results/624323?type=raw                                                   PASSED.
        //Test case 24: https://nj.milesplit.com/meets/412104-westfield-vs-rahway-2021/results/722112?type=raw                                             PASSED
        //Test case 25: https://nj.milesplit.com/meets/616841-union-county-championship-relays-2024/results/1044911?type=raw                               PASSED.





















    }
}
