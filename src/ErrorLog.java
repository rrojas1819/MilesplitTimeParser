
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ErrorLog {

    private static Path pathToFile;
    private static File errorTextLog;
    private static int num = 0;
    private static BufferedWriter bufferedWriter;
    private static FileWriter FileToWrite;
    private static String ExceptionStatement;


    public static void writeIntoLog(Exception error,String LinkOfMeet,String nameOfSchool)  {
        createTxtFile(getErrorType(error));
        try {
            FileToWrite = new FileWriter(errorTextLog);
            bufferedWriter = new BufferedWriter(FileToWrite);
            whatToWriteIntoLog(LinkOfMeet,nameOfSchool);
        } catch (IOException e) {
            System.out.println(e);
        }



    }


    private static void whatToWriteIntoLog(String LinkOfMeet,String nameOfSchool) throws IOException {
        bufferedWriter.write(
                String.format((new Date()) + "\n" +
                        "Link: " + LinkOfMeet + "\n" +
                        "School: " + nameOfSchool + "\n" +
                        "Error Statement: "+ExceptionStatement + "\n"

                        )
        );
        bufferedWriter.close();

    }
    private static String getErrorType(Exception error) {

        String toSplit = error.toString();
        String[] values = toSplit.split(":");
        String[] finalValues = values[0].split("\\.");
        ExceptionStatement = values[1];
        return finalValues[finalValues.length-1];
    }


    private static void createTxtFile(String errorName) {
        pathToFile = Paths.get("./ErrorLog/"+errorName+"Logs/ "+errorName + "_"+ num +".txt");
        errorTextLog = new File(String.valueOf(pathToFile));
        while(Files.exists(pathToFile)){
            num++;
            pathToFile = Paths.get("./ErrorLog/"+errorName+"Logs/ "+errorName + "_"+ num +".txt");
            errorTextLog = new File(String.valueOf(pathToFile));
        }

        try {
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);

        }
        catch (IOException e) {

        }

    }


    
}
class ForcedException extends  Exception{
    public ForcedException(){
        super("Forced Exception");
    }
    public ForcedException(String s) {
        super(s);
    }
}
class AddressNotReadingException extends Exception{
    public AddressNotReadingException(){
        super("Address isn't reading or doesn't exist");
    }
    public AddressNotReadingException(String s) {
        super(s);
    }
}
class MatchNotFoundException extends Exception{
    public MatchNotFoundException(){
        super("Match group was not found in the switchCase Function");
    }
    public MatchNotFoundException(String s) {
        super(s);
    }

}
