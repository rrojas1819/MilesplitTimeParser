import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.    select.Elements;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class runTrackProgram {
    public static void main(String[] args) throws IOException {
        new InputField();








    }
    //"https://nj.milesplit.com/meets/548099-trials-of-miles-xc-opening-night-presented-by-new-balance-2023/results/955421?type=raw","Rahway", false\

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


public class TrackProgram extends JFrame {
    /***
     * Group 4 is comma
     * Group 15 is second time event
     * Group 6 is one or more names
     * Group 3 is the first time
     *
     ***/
    private String school,FullResults;
    private Document doc;
    private Element meetResultsBody;
    private Elements results;
    private HashMap<String,Athlete> athleteMap = new LinkedHashMap<>();
    private static ArrayList<HashMap<String,Athlete>> hashMapArrayList = new ArrayList<>();
    private ArrayList<Athlete> schoolAthletes;
    //countSchool is for testing purposes, no real application.
    private Pattern pat;
    private Matcher match;
    private static final String[] states = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VA", "VT", "WA", "WI", "WV", "WY"};
    public boolean IsEmpty = false;
    private CSVWriter Writer;

    /***
     * Constructor function for TrackProgram, sets up JavaSoup as well as the match and pattern regex and lastly creates the ArrayList for athletes
     * @param input InputField Class Object
     * @throws IOException
     */
    public TrackProgram(InputField input) throws IOException  {

        school = input.getSchoolName();
        doc = Jsoup.connect(input.getAddress()).get();
        meetResultsBody = doc.getElementById("meetResultsBody");
        results = meetResultsBody.getElementsByTag("pre");
        schoolAthletes = new ArrayList<>();
        FullResults = results.first().text();
        pat = Pattern.compile("\\s*?(\\d+|--)\\s+(#\\s+\\d+\\s+)?(\\s*[a-zA-Z-']+\\s*)+(,)?\\s+([a-zA-Z-']+)(\\s+([a-zA-Z-']+))*?\\s+(\\d+?\\s+)?" + school + "\\s*(([a-zA-Z-'()]+)\\s+)?(,?\\s+[a-zA-Z]+\\s+?)*?" +
                "(ND|NH|DNF|FOUL|DNS|DQ|\\d+[.]\\d+q?m?|\\d+?:\\d+?([.]+?\\d+)?|J?\\d+-\\d+([.]+?\\d+q?)?)+" +
                "[ \\t\\x0B\\f\\r]*(ND|NH|DNF|FOUL|DNS|DQ|\\d+[.]+?\\d+m?|\\d+?:\\d+?[.]+?(\\d+)?|J?\\d+-\\d+([.]+?\\d+q?m?)?|\\d+)?[ \\t\\x0B\\f\\r]*(\\d+[.]\\d+m?|\\d+)?[ \\t\\x0B\\f\\r]*");
        match = pat.matcher(FullResults);


    }



    public ArrayList<HashMap<String,Athlete>> getHashMapArrayList(){
            return hashMapArrayList;
    }


    /***
     * Checks if the string address given actually exists, IE checks if the state is real
     * @param address String, specifically is the string of the abbreviated state IE. "NJ","NY","NC"
     * @return boolean
     */
    public static boolean returnSpecificState(String address){
        address = address.toUpperCase();
        for(String s : states ){
            if(address.matches(s)){
                return true;
            }

        }
        return false;
    }



    /***
     * <p>0 is if no comma exists and the group 15 and group 6 are null</p>
     * <p>1 is if no comma exists and the group 15 is null and group 6 is not null</p>
     * <p>2 is if no comma exists and the group 15 is not null and group 6 is null</p>
     * <p>3 is if no comma exists and the group 15 is not null and group 6 is not null</p>
     * <p>4 is if comma exists and the group 15 and group 6 are null</p>
     * <p>5 is if comma exists and the group 15 is null and group 6 is not null</p>
     * <p>6 is if comma exists and the group 15 is not null and group 6 is null</p>
     * <p>7 is if comma exists and the group 15 is not null and group 6 is not null</p>
     * @return int,
     */
    private int switchCheck(){
        if(match.group(4) == null){
            if(match.group(15) == null && match.group(6) == null) {
                return 0;
            }
            else if(match.group(15) == null && match.group(6) != null) {
                    return 1;
            }
            else if(match.group(15) != null && match.group(6) == null) {
                return 2;
            }
            else if(match.group(15) != null && match.group(6) != null) {
                return 3;
            }

        }
        else if(match.group(4) != null){
            if(match.group(15) == null && match.group(6) == null) {
                return 4;
            }
            else if(match.group(15) == null && match.group(6) != null) {
                return 5;
            }
            else if(match.group(15) != null && match.group(6) == null) {
                return 6;
            }
            else if(match.group(15) != null && match.group(6) != null) {
                return 7;
            }

        }
        return -1;
    }

    /***
     *
     */
    public void Parse() {
        int schoolAthleteCount = 0;
        while (!match.hitEnd()) {
            if (match.find()) {
                IsEmpty = false;
                switch(switchCheck()){
                    case 0:
                        schoolAthletes.add(new Athlete(match.group(3) +" "+ match.group(5),match.group(12)));
                        break;
                    case 1:
                        schoolAthletes.add(new Athlete(match.group(3)+" " + match.group(5) + match.group(6), match.group(12)));
                        break;
                    case 2:
                        schoolAthletes.add(new Athlete(match.group(3)+" " + match.group(5), match.group(15)));
                        break;
                    case 3:
                        schoolAthletes.add(new Athlete(match.group(3)+" " + match.group(5)+ match.group(6), match.group(15)));
                        break;
                    case 4:
                        schoolAthletes.add(new Athlete(match.group(5)+" " + match.group(3),match.group(12)));
                        break;
                    case 5:
                        schoolAthletes.add(new Athlete(match.group(5)+ match.group(6)+" " + match.group(3), match.group(12)));
                        break;
                    case 6:
                        schoolAthletes.add(new Athlete(match.group(5)+" " + match.group(3),match.group(15)));
                        break;
                    case 7:
                        schoolAthletes.add(new Athlete(match.group(5) +match.group(6)+" " + match.group(3),match.group(15)));
                        break;
                    default:
                        System.out.println("Failed");
                        break;


                }
                schoolAthletes.get(schoolAthleteCount).setResultLine(match.group());
                schoolAthletes.get(schoolAthleteCount).selfCheck();
                schoolAthletes.get(schoolAthleteCount).checkTimeVar();
                //schoolAthletes.get(schoolAthleteCount) is the current Athlete object (B)
                //athleteMap.containsKey(schoolAthletes.get(schoolAthleteCount).getName()) (A)
                if(athleteMap.containsKey(schoolAthletes.get(schoolAthleteCount).getName())){
                    //if the name is in the athlete map this will trigger
                    try {
                        athleteMap.get(schoolAthletes.get(schoolAthleteCount).getName()).copySelf(schoolAthletes.get(schoolAthleteCount));
                    }
                    catch(Exception caught){
                        System.out.println(caught);
                    }

                    //athleteMap.put(schoolAthletes.get(schoolAthleteCount).getName(), schoolAthletes.get(schoolAthleteCount));

                }else {
                    athleteMap.put(schoolAthletes.get(schoolAthleteCount).getName(), schoolAthletes.get(schoolAthleteCount));

                }
                schoolAthleteCount += 1;
            }

        }
        if(schoolAthleteCount == 0){
            IsEmpty = true;
            return;
        }
        int tempvar = 0;
        StringBuilder tempString = new StringBuilder();

        for (Map.Entry<String, Athlete> set : athleteMap.entrySet()) {
            tempString.append(set.getValue());
            tempvar += 1;

        }

        System.out.println(tempString);
        StringSelection stringSelection = new StringSelection(tempString.toString());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        hashMapArrayList.add(athleteMap);
        try {
            CSVWrite(1);
        }catch(IOException a){
           System.out.println(a);
        }

    }



    public int getHashMapArrayListSize() {
        return hashMapArrayList.size();
    }

    public boolean IsEmpty() {
        return IsEmpty;
    }

    public void CSVWrite(int submissionCount) throws IOException {
        Writer = new CSVWriter(hashMapArrayList.get(submissionCount-1));
        Writer.setSubmissionCount(submissionCount-1);
        Writer.givenDataArray_whenConvertToCSV_thenOutputCreated();
    }


    class CSVWriter{
        //baeldung https://www.baeldung.com/java-csv#:~:text=In%20this%20quick%20tutorial%2C%20we,and%20how%20to%20handle%20them.
        /***
         * I have two choices I can either integreate this in a way where it organizes the names and times from what I have parsed
         *
         * Another choice is to completely revamp the parsing formula to work for everything. Which is honestly better in the long run and also allows for group usage via patterns and matchers
         */// Should just revamp everything atp
        private HashMap<String,Athlete> athleteDataMap;
        private Stream<Athlete> stream;
        private int submissionCount;
        public CSVWriter(HashMap<String,Athlete> athleteDataMap){
            this.athleteDataMap = athleteDataMap;
            stream = athleteMap.values().stream();

        }

        public void setSubmissionCount(int submissionCount) {
            this.submissionCount = submissionCount;
        }

        public void givenDataArray_whenConvertToCSV_thenOutputCreated() throws IOException {
            Path pathToFile = Paths.get("./Submissions/Submission" + submissionCount+ ".csv");
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
            File csvOutputFile = new File(String.valueOf(pathToFile));//Need the submissions to either be numbered or collected from the meet name

            try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
                stream.map(this::convertToCSV)
                        .forEach(pw::println);
            }
        }
        private String convertToCSV(Athlete data) {

            return Stream.of(data)
                    .map(this::escapeSpecialCharacters)
                    .collect(Collectors.joining(","));
        }
        //Fix this with a format
        private String escapeSpecialCharacters(Athlete data1) {
            if (data1 == null) {
                throw new IllegalArgumentException("Input data cannot be null");
            }
            String data = data1.toString();
            String escapedData = data.replaceAll("[ \\t\\x0B\\f]",",");
            if (data.contains(",") || data.contains("\"") || data.contains("'")) {
                data = data.replace("\"", "\"\"");
                escapedData = "\"" + data + "\"";
            }

            return escapedData.substring(0,escapedData.length()-1);//-1 to remove the final \n from the athlete object to string return
        }
    }

}
