import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class InputField extends JFrame {

    private JTextField URL,schoolName;
    private String name, address = null;
    private JButton submit,clearButton; //Checks for correct input from text fields.
    private JLabel notification,URLLabel,schoolLabel,subLabel;
    private JPanel inputPanel,urlPanel,schoolPanel,checkPanel,buttonBox,subPanel;
    private boolean checkFail = false;
    private JComboBox submissionDrop;
    private int submissionCount = 1;
    private JTextArea textArea;

    private TrackProgram program;

    /***
     * Checks if the permisssions.txt is set to true when the program first starts.
     * @return
     */
    public boolean checkPermissions(){
        return true;
    }
    /***
     * Creates the GUI and directory for the permissions.txt
     * @return
     */
    public void createPermissions(){

    }
    public InputField() {
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
        this.setVisible(true);



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
                                program = new TrackProgram(this);
                                System.out.println("Freezing here 2");

                            }catch (IOException | IllegalArgumentException | NullPointerException ill) {
                                notification.setForeground(Color.RED);
                                notification.setText("Error with Address!");
                                checkFail = true;
                            }
                            catch(Exception anythingElse){
                                ErrorLog.writeIntoLog(anythingElse,address,name);
                            }
                            if (!checkFail && TrackProgram.returnSpecificState(m.group(4))) {
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
