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
        Document doc = Jsoup.connect("https://nj.milesplit.com/meets/548099-trials-of-miles-xc-opening-night-presented-by-new-balance-2023/results/955421?type=raw").get();
        Element meetResultsBody = doc.getElementById("meetResultsBody");
        Elements results = meetResultsBody.getElementsByTag("pre");
        String fullResults = results.text();





        //Test case 1:  https://nj.milesplit.com/meets/452060-the-varsity-classic-2023/results/873324?type=raw                                             PASSED              initial (by commas)
        //Test case 2:  https://nj.milesplit.com/meets/548099-trials-of-miles-xc-opening-night-presented-by-new-balance-2023/results/955421?type=raw       PASSED           \t
        //Test case 3:  https://nj.milesplit.com/meets/511411-njsiaa-sectional-championships-north-2-group-2and3-2023/results/873249?type=raw                          No commas
        //Test case 4:  https://nj.milesplit.com/meets/467245-rahway-rising-stars-invitational-1-2022/results/800004                                                   Empty space for teams
        //Test case 5:  https://nj.milesplit.com/meets/530391-magee-memorial-class-meet-2023/results/963795?type=raw                                               worked
        //Test case 6:  https://ny.milesplit.com/meets/371240-coach-glynn-holiday-carnival-2020/results/683003?type=raw                                    PASSED        2 pres
        //Test case 7:  https://nj.milesplit.com/meets/420556-blue-devil-classic-2021/results/740378?type=raw                                                       "char"
        //Test case 8:  https://nj.milesplit.com/meets/483051-union-county-championship-relays-2023/results/924436?type=raw                                          %
        //Test case 9:  https://nj.milesplit.com/meets/509578-garden-state-showcase-2-2022/results                                                                  Single character
        //Test case 10: https://nj.milesplit.com/meets/543091-union-county-jv-championship-2023/results/928511?type=raw                                              -----WE GOOD-----(so far)
        //Test case 11: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw                                              "Was reading relay names"
        //Test case 12: https://nj.milesplit.com/meets/548750-union-county-conference-championships-2023/results/933203?type=raw
        //Test case 13: https://nj.milesplit.com/meets/505896-sjtca-winter-meet-14-2023/results/874245?type=raw
        //Test case 14: https://nj.milesplit.com/meets/516266-holmdel-twilight-series-royal-rumble-2023/results/936820?type=raw
        //Test case 15: https://nj.milesplit.com/meets/501587-the-egg-club-invitational-2023/results/871160?type=raw
        //Test case 16: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw
        //Test case 17: https://ny.milesplit.com/meets/535855-track-night-nyc-2023/results/939429?type=raw
        //Test case 18: https://ny.milesplit.com/meets/497859-the-armory-frosh-novice-meet-2023/results                                                         "ND" adde
        //Test case 19: https://nj.milesplit.com/meets/461526-blue-devil-classic-2022/results/806813
        //Test case 20: https://ny.milesplit.com/meets/327901-armory-coaches-hall-of-fame-invitational-2019/results
        //Test case 21: https://ny.milesplit.com/meets/327891-spike-shoe-holiday-festival-2019/results/622770?type=raw
        //Test case 22: https://nj.milesplit.com/meets/335142-mid-winter-classic-2019/results/624531?type=raw
        //Test case 23: https://nj.milesplit.com/meets/334342-lavino-relays-2019/results/624323?type=raw
        //Test case 24: https://nj.milesplit.com/meets/412104-westfield-vs-rahway-2021/results/722112?type=raw


        //System.out.println(fullResults);
        /***
         *   1 Jones, Dallas              9 Piscataway T              6.89q  5
         *   2 Addison, Nasir             9 North Star A              7.06q  7
         *   3 Powell, Xavion             9 North Star A              7.21q 11
         *   4 Wright, Irijah             9 North Star A              7.27q  8
         *   5 Wason Taylor, Khalil       9 Piscataway T              7.28q  9
         *
         *
         *   (^((https://?([a-z]{0,2}))|[a-z]{0,2})\.)??milesplit.*type=raw
         *
         *   (^\\d+)?.*((\\w*)(,)?(\\w*)).*(^\\d+)?.*(" +school +")"
         */
        //String d = "1 Jones, Dallas              9 Rahway HS              6.89q  5";
        //String d2 =  "  4 Vincent, Destin            9 Rahway HS                10.36    4 Vincent, Destin            9 Rahway HS                10.36  ";
        //Pattern p = Pattern.compile("\\s*?(\\d+|--)\\s+([a-zA-Z-']+),?\\s+([a-zA-Z-']+)\\s+(\\d+?\\s+)?" + "Rahway" +"\\s+([a-zA-Z-']+)\\s+(ND|\\d+[.]\\d+q?|\\d+?:\\d+?[.]?\\d+?|\\d+-\\d+[.]\\d+q?\\s+?\\d+?)\\s*?(\\d+[.]\\d+\\s+?|\\d+)?");


       // -- Vertil, Davin 11 Rahway HS ND 3                                                                                                     //(\s+\d+[.]\d+\s+)? error maybe >?                                              \\35-08.00q
        // 40 Kori Dudley Rahway HS 18:02.91                                                                                                                                    \\ \\d+?:\d+?[.]?\d+?
        Pattern p2 = Pattern.compile("\\s*?(\\d+|--)\\s+([a-zA-Z-']+),?\\s+([a-zA-Z-']+)\\s+(\\d+?\\s+)?" + school + "\\s+(([a-zA-Z-']+)\\s+)?" +
                "(ND|\\d+[.]\\d+q?|\\d+?:\\d+?[.]+?\\d+?|\\d+-\\d+[.]+?\\d+q?)+" +
                "[ \\t\\x0B\\f\\r]*(\\d+[.]+?\\d+|\\d+)?\\s*(\\d+[.]\\d+)?\\s*");
        //Matcher m = p.matcher(d);
        Matcher m2 = p2.matcher(fullResults);

        // 14 Simpson, Rolando 10 Rahway HS 23.20 2 23.198
        // 15 Wilson, Cody 12 Rahway HS 23.23 4

        //System.out.println(m.matches());
        //System.out.println(m.group(0));
        int count = 0;

        while(!m2.hitEnd()){

           if(m2.find()){
               count++;
               System.out.println("Current Count: " + count + " " + m2.group(0));
            }
        }


























    }
}
