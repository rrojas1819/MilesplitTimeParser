import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Athlete {
    private String name,fieldEventVar,time,event,resultLine;
    private String[] events,timeArray;
    private ArrayList<String> timeArrayList = new ArrayList<>() , eventArrayList = new ArrayList<>();

    public Athlete(String rawName, String[] Ev, String Time, Boolean reverseChoice){
        name = rawName;
        events = Ev;
        time = Time;
        if(reverseChoice){
            reverseName();
        }
    }
    public Athlete(String rawName, Boolean reverseChoice){
        name = rawName;
        if(reverseChoice){
            reverseName();
        }

    }
    public Athlete(String rawName, String Time , Boolean reverseChoice){
        name = rawName;
        time = Time;
        if(reverseChoice){
            reverseName();
        }
    }

    public Athlete(String name) {
        this.name =name;
    }
    public Athlete(String name, String time) {
        this.name =name;
        this.time = time;
    }


    public void reverseName(){
/*
Need to account for space infront of name for formatting

 */



        String[] tempNameArray = name.split(",");
        String lastName = tempNameArray[0].strip();
        String firstName = tempNameArray[1].strip();




        String reversedName = firstName + " " + lastName;
        if (reversedName.contains("\t")){
            reversedName.substring(1);
        }

        name = reversedName;


    }
    public String getFirstName(){

        String[] firstname = name.split(" ");
        return firstname[0];

    }

    public void selfCheck(){
        Pattern pat = Pattern.compile("\\s*?(\\d+|--)\\s+(#\\s+\\d+\\s+)?(\\s*[a-zA-Z-']+\\s*)+(,)?\\s+([a-zA-Z-']+)(\\s+([a-zA-Z-']+))*?\\s+(\\d+?\\s+)?" +"[a-zA-Z-'()]+" +"\\s*(([a-zA-Z-'()]+)\\s+)?(,?\\s+[a-zA-Z]+\\s+?)*?" +
                "(ND|NH|DNF|FOUL|DNS|DQ|\\d+[.]\\d+q?m?|\\d+?:\\d+?([.]+?\\d+)?|J?\\d+-\\d+([.]+?\\d+q?)?)+" +
                "[ \\t\\x0B\\f\\r]*(ND|NH|DNF|FOUL|DNS|DQ|\\d+[.]+?\\d+m?|\\d+?:\\d+?[.]+?(\\d+)?|J?\\d+-\\d+([.]+?\\d+q?m?)?|\\d+)?[ \\t\\x0B\\f\\r]*(\\d+[.]\\d+m?|\\d+)?[ \\t\\x0B\\f\\r]*");
        Matcher match = pat.matcher(resultLine);
        //System.out.println(resultLine);

        if(match.find()){
            Pattern temp = Pattern.compile("\\d{1,3}[.'-]??");
            if(match.group(15) == null){
                return;
            }
            Matcher tempMatch = temp.matcher(match.group(15));
            if(tempMatch.find()){
                this.time = match.group(12);
            }

        }


    }

    public String getName(){
        return name;
    }
    public void checkTimeVar(){
        time = time.strip();
        checkTimeVarHelper(time);
    }

    private void checkTimeVarHelper(String time) {
        if(time.contains("-")){
            fieldEventVar = time;
            this.time = null;

            String[] tempFieldEventVar = fieldEventVar.split(" ");
            fieldEventVar = tempFieldEventVar[0];
            eventArrayList.add(fieldEventVar);
        } else if (time.contains(":") && !time.contains("-")|| time.contains(".") && !time.contains("-")) {
            String[] tempTime = time.split(" ");
            time = tempTime[0];
            timeArrayList.add(time);
        }
    }

    public String getTimeArrayValue(int value){
        return timeArray[value];
    }

    public void setTimeArray(String newTime){

        timeArray = new String[]{time, newTime};
    }
    public void setName(String newName){
        name = newName;
    }
    public void setTime(String newTime){
        time = newTime;
    }
    public void setEvent(String[] newEvent){
        events = newEvent;
    }
    public String getTime() {
        return time;
    }
    public String getFieldEventVar(){
        return fieldEventVar;
    }

    public String getResultLine(){
            return resultLine;
    }
    public void setResultLine(String newResultLine){
        resultLine = newResultLine;
    }
    public String toString(){

        /*
        if(timeArray.length > 1){
            return name+ "\t" + " TIMES " + "\t" + getTimeArrayValue(0) + " " +getTimeArrayValue(1) + "\n"  ;
        }

         */
        if(!eventArrayList.isEmpty()){
            StringBuilder tempString = new StringBuilder();
            tempString.append(name).append("\t").append("FIELD_EVENT").append("\t").append(eventArrayList.get(0)).append("\n");

            for(int i = 1 ; eventArrayList.size() >i;i++){
                tempString.append(name).append("\t").append("FIELD_EVENT").append("\t").append(eventArrayList.get(i)).append("\n");
            }

            return tempString.toString();
        }
        if(!timeArrayList.isEmpty()){
            StringBuilder tempString = new StringBuilder();
            tempString.append(name).append("\t").append("TIMES").append("\t").append(timeArrayList.get(0)).append("\n");
            for(int i = 1 ; timeArrayList.size() >i;i++){
                tempString.append(name).append("\t").append("TIMES").append("\t").append(timeArrayList.get(i)).append("\n");
            }
            return tempString.toString();
        }
        else
            return name + "\t" + "TIME" + "\t" + time+ "\n";

    }


    public void copySelf(Athlete selfAthlete) throws Exception {
        if(!selfAthlete.getName().equals(name)){
            throw new Exception("Not the same name");
        }
        checkTimeVarHelper(selfAthlete.getTime());
        


    }
}


