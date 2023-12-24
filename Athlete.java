public class Athlete {
    private String name;
    private String[] events;

    private String fieldEventVar;

    private String time;

    private String event;
    private String[] timeArray;
    private String resultLine;



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



    public String getName(){
        return name;
    }
    public void checkTimeVar(){
        time = time.strip();

        if(time.contains("-")){
            fieldEventVar = time;
            time = null;

            String[] tempFieldEventVar = fieldEventVar.split(" ");
            fieldEventVar = tempFieldEventVar[0];

        } else if (time.contains(":") && !time.contains("-")|| time.contains(".") && !time.contains("-")) {
            String[] tempTime = time.split(" ");
            time = tempTime[0];
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

        if(time == null){
            return name + "\t" + " FIELD EVENT "+ "\t"+fieldEventVar+ "\n";
        }
        if(timeArray == null){
            return name + "\t"+ " TIME "+ "\t" + time+ "\n";
        }
        if(timeArray.length > 1){
            return name+ "\t" + " TIMES " + "\t" + getTimeArrayValue(0) + " " +getTimeArrayValue(1) + "\n"  ;
        }
        else
            return name + "\t" + " TIME " + "\t" + time+ "\n";

    }


}


