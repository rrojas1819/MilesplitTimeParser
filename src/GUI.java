
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class GUI extends JFrame {

    private JTextField URL,schoolName;
    private String name, address = null;
    private JButton submit,clearButton; //Checks for correct input from text fields.
    private JLabel notification,URLLabel,schoolLabel,subLabel;
    private JPanel inputPanel,urlPanel,schoolPanel,checkPanel,buttonBox,subPanel;
    private boolean checkFail = false;
    private JComboBox submissionDrop;
    private int submissionCount = 1;
    private JTextArea textArea;
    private ParserBackEnd program;

    /***
     * Checks if the permisssions.txt is set to true when the program first starts.
     * @return
     */

    /***
     * Creates the GUI and directory for the permissions.txt
     * @return
     */

    public GUI() {
        PermissionMenu popUp = new PermissionMenu(this);
        boolean quickCheck = false;
        if(popUp.returnStatus()){
            popUp.dispose();
            quickCheck = true;
        }
        this.setTitle("Milesplit Parser");;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1100,375);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;

        Toolkit kit = getToolkit();
        Dimension size = kit.getScreenSize();
        this.setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        subLabel = new JLabel("Submission DropDown:");
        subPanel = new JPanel(new GridBagLayout());
        buttonBox = new JPanel(new GridBagLayout());
        URL = new JTextField(20);
        schoolName = new JTextField(20);
        submit = new JButton("Submit");
        clearButton = new JButton("Clear");
        submit.setFocusable(false);
        clearButton.setFocusable(false);
        buttonBox.add(clearButton);
        buttonBox.add(submit);
        inputPanel = new JPanel(new GridBagLayout());
        urlPanel = new JPanel();
        schoolPanel = new JPanel();
        checkPanel = new JPanel();


        URLLabel = new JLabel("URL: ");
        schoolLabel = new JLabel("School Name: ");


        notification = new JLabel("Please input your Data!");
        buttonPrompts();
        urlPanel.add(URLLabel);
        schoolPanel.add(schoolLabel);

        urlPanel.add(URL);
        schoolPanel.add(schoolName);
        inputPanel.add(urlPanel);
        inputPanel.add(schoolPanel);
        inputPanel.add(checkPanel);
        inputPanel.add(notification);


        c.ipady = 15;

        submissionDrop = new JComboBox();
        GridBagConstraints l = new GridBagConstraints();
        l.ipadx = 40;
        subPanel.add(subLabel,l);
        subPanel.add(submissionDrop);
        GridBagConstraints d = new GridBagConstraints();
        d.ipady = 5;
        d.anchor = GridBagConstraints.FIRST_LINE_START;


        JPanel ne = new JPanel();
        //ne.setBackground(Color.cyan);
        ne.setPreferredSize(new Dimension(500,75));
        textArea = new JTextArea(5,40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        ne.add(scrollPane);
        this.add(subPanel,d);
        this.add(inputPanel,c);
        this.add(buttonBox,c);
        this.add(ne, c);

        if(quickCheck){
            this.setVisible(true);
        }


        submissionDrop.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED ) {
                if(program.getHashMapArrayListSize() > 0) {
                    textArea.setText("");
                    int num = Integer.parseInt(e.getItem().toString().substring(11));
                    num--;
                    System.out.println( program.getHashMapArrayList().get(num).size());
                    program.getHashMapArrayList().get(num).forEach((key, value) -> {
                        //System.out.println("Key=" + key + ", Value=" + value);
                        //use string or collectorbox
                        textArea.append(value.toString());


                    });
                }

            }
        });

    }

    private void buttonPrompts() {
        submit.addActionListener(e -> {
                    if(URL.getText().isEmpty() && schoolName.getText().isEmpty()){
                        URL.requestFocus();
                        notification.setText("Please fill in your data!");
                        notification.setForeground(Color.RED);

                    }
                    else if(URL.getText().isEmpty()){
                        URL.requestFocus();
                        notification.setText("Please fill in your URL!");
                        notification.setForeground(Color.RED);
                    }
                    else if (schoolName.getText().isEmpty()){
                        schoolName.requestFocus();
                        notification.setText("Please fill in your School Name!");
                        notification.setForeground(Color.RED);
                    }
                    else {



                        name = schoolName.getText();
                        address = URL.getText();

                        //need to check if https://
                        //is there as well as need to check if milesplit is there
                        //Create a pattern that checks for  (  https://{}*milesplit{}=raw  )

                        Pattern p = Pattern.compile("(^((https://?([a-z]{0,2}))|[a-z]{0,2})\\.)??milesplit.*");
                        Matcher m = p.matcher(address);


                        if (m.matches()) {
                            checkFail = false;

                            try {
                                program = new ParserBackEnd(this);


                            }catch (IOException | IllegalArgumentException | NullPointerException ill) {
                                notification.setForeground(Color.RED);
                                notification.setText("Error with Address or Name!");
                                checkFail = true;
                                ErrorLog.writeIntoLog(ill,address,name);
                            }
                            catch(Exception anythingElse){
                                ErrorLog.writeIntoLog(anythingElse,address,name);
                            }
                            if (!checkFail && ParserBackEnd.returnSpecificState(m.group(4))) {
                                program.Parse();
                                if(program.IsEmpty()) {
                                    notification.setForeground(Color.RED);
                                    notification.setText("No data under that school");
                                    return;
                                }
                                notification.setForeground(Color.BLACK);
                                notification.setText("Data Copied, to paste use (CTRL V)!");
                                submissionDrop.addItem("Submission " + submissionCount);
                                submissionCount++;

                            }

                        }
                        else{
                            notification.setForeground(Color.RED);
                            notification.setText("Address didn't work!");
                            ErrorLog.writeIntoLog(new AddressNotReadingException(),address,name);
                        }

                    }

                }
        );

        clearButton.addActionListener(e -> {
            URL.setText("");
            schoolName.setText("");
            notification.setForeground(Color.BLACK);
            notification.setText("Please input your Data!");
        });

        submit.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                submit.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                submit.setBackground((new JButton()).getBackground());
            }
        });
        clearButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                clearButton.setBackground(Color.gray);
            }

            @Override
            public void mouseExited(MouseEvent e) {

                clearButton.setBackground((new JButton()).getBackground());
            }
        });



    }


    public String getAddress(){
            return address;

    }
    public String getSchoolName(){
        return name;
    }


    public String toString(){
        return null;
    }

}

/***
 * Creates a Permission Menu at start up that is inheriting from JFrame, if the checkBox is selected then It never pops up again unless the Permission Menu is changed.(Doing so would likely throw an Exception)
 */
class PermissionMenu extends JFrame{
    //This technically isn't all GUI//
    private FileWriter fileToWrite;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private Path pathToFile = Paths.get("./Permissions/Permission.txt");
    private File Permission = new File(String.valueOf(pathToFile));
    private boolean status = false;
    private JCheckBox checkBox = new JCheckBox();


    public PermissionMenu(JFrame frame){
        this.setTitle("READ ME");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,390);
        this.setLayout(new GridBagLayout());
        Toolkit kit = getToolkit();
        Dimension size = kit.getScreenSize();
        this.setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);
        JPanel textPanel = new JPanel(new FlowLayout());
        textPanel.setPreferredSize(new Dimension(415,250));
        textPanel.setBackground(Color.lightGray);
        JLabel label = new JLabel();
        label.setText("<html><p>BE AWARE ideally 3 main folders will be created, the Permission folder,<br> " +
                "the Submissions Folder, and the ErrorLog folder<br><br>1. Submission Folder will have the submissions<br><br>2. " +
                "Permission Folder will have Permission.txt which controls this pop up<br>" +
                "<br>3. ErrorLog will track errors and the time to log for the developer to fix <br>" +
                "<br> To EMPHASIZE you will have to email or submit the issues through Github <br>or Google Forms<br>" +
                "<br>HOWEVER, the ErrorLog folder will only occur if given permission</p></html>");
        JLabel checkboxLab = new JLabel("Select if you have read: ");
        textPanel.add(label);
        JLabel lab = new JLabel("<html><br>The developer cannot access the files! ONLY YOU CAN!</html>");
        lab.setForeground(Color.RED);
        textPanel.add(lab);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        c.ipady = 20;
        GridBagConstraints d = new GridBagConstraints();
        d.anchor = GridBagConstraints.FIRST_LINE_END;
        d.gridx = 0;
        d.ipady = 10;
        this.add(textPanel,c);
        buttonPrompts(frame,buttonPanel);
        buttonPanel.add(checkboxLab,d);
        buttonPanel.add(checkBox,d);
        this.add(buttonPanel,c);
        try {
            if(checkPermission()){
                status = true;
            }else{
                this.setVisible(true);
            }
        } catch (IOException e) {
        }
    }


    public boolean returnStatus(){
        return status;
    }
    private void buttonPrompts(JFrame main, JPanel buttonPanel){
        JButton submit = new JButton("Continue");
        submit.setFocusable(false);
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridx = 0;
        buttonPanel.add(submit,c);
        submit.addActionListener(e ->{
            try {if(checkBox.isSelected()) {
                writeTruePermission();
                main.setVisible(true);
                this.dispose();
            }
            else{
                main.setVisible(true);
                this.dispose();
            }
            } catch (IOException ex) {
            }
        });
    }


    private void writeTruePermission() throws IOException    {
        if(Permission.exists()){
            Permission.delete();
        }

        try {
            pathToFile = Paths.get("./Permissions/Permission.txt");
            Permission = new File(String.valueOf(pathToFile));
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
        } catch (IOException e) {
        }
        fileToWrite = new FileWriter(Permission);
        bufferedWriter = new BufferedWriter(fileToWrite);
        bufferedWriter.write("true");
        bufferedWriter.close();
    }

    private boolean checkPermission() throws IOException {
        if(Permission.exists() && !Permission.isDirectory()){
            fileReader = new FileReader(Permission);
            bufferedReader = new BufferedReader(fileReader);
            String toRead = bufferedReader.readLine();
            try {
                if (toRead.compareTo("false") == 0) {
                    return false;
                } else if (toRead.compareTo("true") == 0) {
                    return true;
                }
            }
            catch(NullPointerException e ){
                Permission.delete();

                return false;
            }

       }
        else{
            createPermission(pathToFile);
            fileToWrite = new FileWriter(Permission);
            bufferedWriter = new BufferedWriter(fileToWrite);
            writeFalseIntoPermission();
        }
         return false;
    }
    private void writeFalseIntoPermission() throws IOException {
        bufferedWriter.write(String.format(("false")));
        bufferedWriter.close();
    }
    private void createPermission(Path pathToFile){
        try {
            Files.createDirectories(pathToFile.getParent());
            Files.createFile(pathToFile);
        } catch (IOException e) {
        }
    }


}