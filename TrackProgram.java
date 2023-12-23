import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class runTrackProgram {
    public static void main(String[] args) throws IOException {
        TrackProgram tracker = new TrackProgram("https://nj.milesplit.com/meets/412104-westfield-vs-rahway-2021/results/722112?type=raw","Rahway", false);
    }
//Test case 1:  https://nj.milesplit.com/meets/452060-the-varsity-classic-2023/results/873324?type=raw                                                Passed        initial (by commas)
//Test case 2:  https://nj.milesplit.com/meets/548099-trials-of-miles-xc-opening-night-presented-by-new-balance-2023/results/955421?type=raw          Passed        \t
//Test case 3:  https://nj.milesplit.com/meets/511411-njsiaa-sectional-championships-north-2-group-2and3-2023/results/873249?type=raw                 Passed         No commas
//Test case 4:  https://nj.milesplit.com/meets/467245-rahway-rising-stars-invitational-1-2022/results/800004                                          Passed         Empty space for teams
//Test case 5:  https://nj.milesplit.com/meets/530391-magee-memorial-class-meet-2023/results/963795?type=raw                                          Passed     worked
//Test case 6:  https://ny.milesplit.com/meets/371240-coach-glynn-holiday-carnival-2020/results/683003?type=raw                                       Passed     2 pres
//Test case 7:  https://nj.milesplit.com/meets/420556-blue-devil-classic-2021/results/740378?type=raw                                                 Passed      "char"
//Test case 8:  https://nj.milesplit.com/meets/483051-union-county-championship-relays-2023/results/924436?type=raw                                   Passed       %
//Test case 9:  https://nj.milesplit.com/meets/509578-garden-state-showcase-2-2022/results                                                            Passed      Single character
//Test case 10: https://nj.milesplit.com/meets/543091-union-county-jv-championship-2023/results/928511?type=raw                                       Passed       -----WE GOOD-----(so far)
//Test case 11: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw                                      Passed        "Was reading relay names"
//Test case 12: https://nj.milesplit.com/meets/548750-union-county-conference-championships-2023/results/933203?type=raw                              Passed
//Test case 13: https://nj.milesplit.com/meets/505896-sjtca-winter-meet-14-2023/results/874245?type=raw                                               Passed
//Test case 14: https://nj.milesplit.com/meets/516266-holmdel-twilight-series-royal-rumble-2023/results/936820?type=raw                               Passed
//Test case 15: https://nj.milesplit.com/meets/501587-the-egg-club-invitational-2023/results/871160?type=raw                                          Passed
//Test case 16: https://pa.milesplit.com/meets/538832-new-balance-nationals-outdoor-2023/results/946068?type=raw                                      Passed
//Test case 17: https://ny.milesplit.com/meets/535855-track-night-nyc-2023/results/939429?type=raw                                                    Passed
//Test case 18: https://ny.milesplit.com/meets/497859-the-armory-frosh-novice-meet-2023/results                                                       Passed  "ND" adde
//Test case 19: https://nj.milesplit.com/meets/461526-blue-devil-classic-2022/results/806813                                                          Passed
//Test case 20: https://ny.milesplit.com/meets/327901-armory-coaches-hall-of-fame-invitational-2019/results                                           Passed
//Test case 21: https://ny.milesplit.com/meets/327891-spike-shoe-holiday-festival-2019/results/622770?type=raw                                        Passed
//Test case 22: https://nj.milesplit.com/meets/335142-mid-winter-classic-2019/results/624531?type=raw                                                 Passed
//Test case 23: https://nj.milesplit.com/meets/334342-lavino-relays-2019/results/624323?type=raw                                                      Passed
//Test case 24: https://nj.milesplit.com/meets/412104-westfield-vs-rahway-2021/results/722112?type=raw                                                Passed
//Test case 25:
//Test case 26:
//Test case 27:
//Test case 28:
//Test case 29:
//Test case 30: 
}


public class TrackProgram {
    private String school;
    private Document doc;
    private Element meetResultsBody;
    private Elements results;
    private ArrayList<Athlete> schoolAthletes;
    private int countSchoolInstance = 0;
    private boolean commaBool;
    private String resultLineNP;


    public TrackProgram(String URL, String schoolname, boolean comma) throws IOException {


        school = schoolname;
        doc = Jsoup.connect(URL).get();
        meetResultsBody = doc.getElementById("meetResultsBody");
        results = meetResultsBody.getElementsByTag("pre");
        schoolAthletes = new ArrayList<>();
        commaBool = comma;
        if(results.size() > 1) {
            results = new Elements(results.last());
        }
        nameStrip();
        timeStrip();






    }

    private void timeStrip() {  // 33-3 Doesn't work as a event or time
        for(Athlete ath : schoolAthletes){
            int timeCounter = 0;
            if (ath.getResultLine().contains("  ")) {


                for (String splitResultLine : ath.getResultLine().split("  ")) {
                    splitResultLine = splitResultLine.strip();
                    if (!splitResultLine.contains("%") && (splitResultLine.contains(".") && !splitResultLine.contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*")  ||  !splitResultLine.contains("%") && splitResultLine.contains(":") && !splitResultLine.contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*")) ||  !splitResultLine.contains("%") && (splitResultLine.contains("-") && !splitResultLine.contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*"))) {


                        if (timeCounter == 0) {

                                if (ath.getTime() == "ND" || ath.getTime() == "FOUL") {
                                    continue;
                                }


                                ath.setTime(splitResultLine);
                                timeCounter += 1;



                        } else if (timeCounter > 0) {

                            ath.setTimeArray(splitResultLine);

                        }


                    }
                }
            } else if (ath.getResultLine().contains("\t")) {
                for (String splitResultLine : ath.getResultLine().split("\t")) {
                    splitResultLine = splitResultLine.strip();
                    if (!splitResultLine.contains("%") && (splitResultLine.contains(".") && !ath.getResultLine().contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*") ||   !splitResultLine.contains("%") && splitResultLine.contains(":") && !ath.getResultLine().contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*")) || !splitResultLine.contains("%") && (splitResultLine.contains("-") && !ath.getResultLine().contains("'") && !splitResultLine.contains("Jr") && splitResultLine.matches(".*\\d.*"))) {


                        if (timeCounter == 0) {

                                if (ath.getTime() == "ND" || ath.getTime() == "FOUL") {
                                    continue;
                                }


                                ath.setTime(splitResultLine);
                                timeCounter += 1;




                        } else if (timeCounter > 0) {

                            ath.setTimeArray(splitResultLine);



                        }


                    }
                }

            }

        }



        int tempvar = 0;
        for (Athlete a : schoolAthletes) {
            try {
                a.checkTimeVar();
            } catch (NullPointerException e) {
                continue;
            }


            System.out.println(a + "Index " + tempvar);
            tempvar += 1;
        }

    }


    private void nameStrip() {
        String[] result_Stripped;

        if(results.text().contains("\r")){
            result_Stripped = results.text().split("\r"); //splits the doc into full lines
        } else {
            result_Stripped = results.text().split("\n"); //splits the doc into full lines
        }


        int schoolAthleteCount = 0;


        if (SchoolListCheck()) { //Checks if the school is in the doc
            for (String resultLine : result_Stripped) {  //checks each line in the array of school lines

                if (resultLine.contains(school)) {
                    countSchoolInstance += 1;

                    if(resultLine.matches("^(\\W+?|\\d+?)?.*")){

                        Pattern pattern = Pattern.compile("([a-zA-Z]).*");
                        Matcher matcher = pattern.matcher(resultLine);

                        if(matcher.find()) {
                            String toBeCut = matcher.group(1);


                            resultLineNP = resultLine.substring(resultLine.indexOf(toBeCut));

                        }


                    }


                    System.out.println(resultLineNP + "LINE" + schoolAthleteCount);



                    if (resultLineNP.contains("  ") && commaBool) {
                        for (String splitResultLine : resultLineNP.split("  ")) {
                            splitResultLine = splitResultLine.strip();

                            if ((!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w") && !splitResultLine.matches(".*\\d.*") && !splitResultLine.contains(".") && !splitResultLine.isEmpty() && resultLine.contains(",") && !splitResultLine.contains(school)) || (!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w") && !splitResultLine.matches(".*\\d.*") && splitResultLine.contains("Jr") && !splitResultLine.isEmpty() && resultLine.contains(",") && !splitResultLine.contains(school))) {
                                //Checks if it's the name line (if so it continues) (Specifically checks if the line contains numbers)


                                if (splitResultLine.contains("ND")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("ND");
                                    schoolAthleteCount += 1;
                                    continue;


                                }


                                if (splitResultLine.contains("FOUL")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("FOUL");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNS")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNS");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("NH")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("NH");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNF")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNF");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DQ")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DQ");
                                    schoolAthleteCount += 1;
                                    continue;
                                }


                                schoolAthletes.add(new Athlete(splitResultLine, true));
                                schoolAthletes.get(schoolAthleteCount).setResultLine(resultLineNP);
                                schoolAthleteCount += 1;


                            }


                        }
                    } else if (resultLineNP.contains("  ") && !commaBool) {
                        for (String splitResultLine : resultLineNP.split("  ")) {
                            splitResultLine = splitResultLine.strip();
                            if ((!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w") && !splitResultLine.matches(".*\\d.*") && !splitResultLine.contains(".") && !splitResultLine.isEmpty() && !splitResultLine.contains(school)) || (!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w") && !splitResultLine.matches(".*\\d.*") && splitResultLine.contains("Jr") && !splitResultLine.isEmpty() && !splitResultLine.contains(school))) {
                                //Checks if it's the name line (if so it continues) (Specifically checks if the line contains numbers)


                                if (splitResultLine.contains("ND")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("ND");
                                    schoolAthleteCount += 1;
                                    continue;


                                }


                                if (splitResultLine.contains("FOUL")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("FOUL");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNS")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNS");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("NH")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("NH");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNF")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNF");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DQ")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DQ");
                                    schoolAthleteCount += 1;
                                    continue;
                                }


                                schoolAthletes.add(new Athlete(splitResultLine, commaBool));
                                schoolAthletes.get(schoolAthleteCount).setResultLine(resultLineNP);
                                schoolAthleteCount += 1;


                            }


                        }
                    } else if (resultLineNP.contains("\t") && commaBool) {
                        if ("\t".equals(String.valueOf(resultLineNP.charAt(0)))) {
                            resultLineNP = resultLineNP.substring(1);
                        }

                        for (String splitResultLine : resultLineNP.split("\t")) {
                            splitResultLine = splitResultLine.strip();
                            if ((!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w") &&!splitResultLine.matches(".*\\d.*") && !splitResultLine.contains(".") && !splitResultLine.isEmpty() && resultLine.contains(",") && !splitResultLine.contains(school)) || ( !splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w")&& !splitResultLine.matches(".*\\d.*") && splitResultLine.contains("Jr") && !splitResultLine.isEmpty() && resultLine.contains(",") && !splitResultLine.contains(school))) {
                                //Checks if it's the name line (if so it continues) (Specifically checks if the line contains numbers)


                                if (splitResultLine.contains("ND")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("ND");
                                    schoolAthleteCount += 1;
                                    continue;


                                }


                                if (splitResultLine.contains("FOUL")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("FOUL");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNS")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNS");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("NH")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("NH");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNF")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNF");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DQ")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DQ");
                                    schoolAthleteCount += 1;
                                    continue;
                                }


                                schoolAthletes.add(new Athlete(splitResultLine, true));
                                schoolAthletes.get(schoolAthleteCount).setResultLine(resultLineNP);
                                schoolAthleteCount += 1;


                            }


                        }


                    } else if (resultLineNP.contains("\t") && !commaBool) {
                        if ("\t".equals(String.valueOf(resultLineNP.charAt(0)))) {
                            resultLineNP = resultLineNP.substring(1);
                        }

                        for (String splitResultLine : resultLineNP.split("\t")) {
                            splitResultLine = splitResultLine.strip();
                            if ((!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w")&& !splitResultLine.matches(".*\\d.*") && !splitResultLine.contains(".") && !splitResultLine.isEmpty() && !splitResultLine.contains(school)) || (!splitResultLine.matches("'\\w'") && !splitResultLine.matches("\\w")&& !splitResultLine.matches(".*\\d.*") && splitResultLine.contains("Jr") && !splitResultLine.isEmpty() && !splitResultLine.contains(school))) {
                                //Checks if it's the name line (if so it continues) (Specifically checks if the line contains numbers)

                                if (splitResultLine.contains("ND")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("ND");
                                    schoolAthleteCount += 1;
                                    continue;

                                }

                                if (splitResultLine.contains("FOUL")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("FOUL");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNS")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNS");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("NH")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("NH");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DNF")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DNF");
                                    schoolAthleteCount += 1;
                                    continue;
                                }
                                if (splitResultLine.contains("DQ")) {
                                    schoolAthleteCount -= 1;
                                    schoolAthletes.get(schoolAthleteCount).setTime("DQ");
                                    schoolAthleteCount += 1;
                                    continue;
                                }


                                schoolAthletes.add(new Athlete(splitResultLine, commaBool));
                                schoolAthletes.get(schoolAthleteCount).setResultLine(resultLineNP);
                                schoolAthleteCount += 1;


                            }


                        }


                    }
                }

            }

        }

    }

    private boolean SchoolListCheck() {
        return results.text().contains(school);
    }




}
