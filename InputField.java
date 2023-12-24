import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class InputField extends JFrame {

    private JTextField URL;
    private String name = null;
    private String address = null;
    private boolean commaCheck;
    private JTextField schoolName;
    private JButton checker; //Checks for correct input from text fields.
    private JLabel notification;
    private JLabel URLLabel;
    private JLabel schoolLabel;
    private JPanel inputPanel;
    private JPanel urlPanel;
    private JPanel schoolPanel;
    private JPanel checkPanel;
    private JLabel checkBoxLabel;
    private JCheckBox checkBox;
    private boolean comma;
    private CountDownLatch latch;

    public InputField(CountDownLatch lat){
        latch = lat;
        this.setTitle("Milesplit Parser");;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,135);
        this.setLayout(new FlowLayout());

        Toolkit kit = getToolkit();
        Dimension size = kit.getScreenSize();
        this.setLocation(size.width/2 - getWidth()/2, size.height/2 - getHeight()/2);

        URL = new JTextField(20);
        schoolName = new JTextField(20);
        checker = new JButton("Submit");
        checker.setFocusable(false);
        checkBox = new JCheckBox();
        inputPanel = new JPanel(new GridBagLayout());
        urlPanel = new JPanel();
        schoolPanel = new JPanel();
        checkPanel = new JPanel();

        checkBoxLabel = new JLabel("Select if there is a comma: ");
        URLLabel = new JLabel("URL: ");
        schoolLabel = new JLabel("School Name:");


        notification = new JLabel("Please input your Data!");



        checker.addActionListener(e -> {
            if(URL.getText().length() == 0 && schoolName.getText().length() == 0 ){
                URL.requestFocus();
                notification.setText("Please fill in your data!");
                notification.setForeground(Color.RED);

            }
            else if(URL.getText().length() == 0){
                URL.requestFocus();
                notification.setText("Please fill in your URL!");
                notification.setForeground(Color.RED);
            }
            else if (schoolName.getText().length() == 0){
                schoolName.requestFocus();
                notification.setText("Please fill in your School Name!");
                notification.setForeground(Color.RED);
            }
            else{
                //Need some validation here as well as checking correct input to some degree.

                name = schoolName.getText();
                address = URL.getText();
                latch.countDown();
                comma =  checkBox.isSelected();
            }

        }
        );
        checkPanel.add(checkBoxLabel);
        urlPanel.add(URLLabel);
        schoolPanel.add(schoolLabel);


        checkPanel.add(checkBox);
        urlPanel.add(URL);
        schoolPanel.add(schoolName);
        inputPanel.add(urlPanel);
        inputPanel.add(schoolPanel);
        inputPanel.add(checkPanel);
        inputPanel.add(notification);

        this.add(inputPanel);
        this.add(checker);
        this.setVisible(true);
    }



    public String getAddress(){
            return address;

    }
    public String getSchoolName(){
        return name;
    }
    public boolean getBoolComma(){
        return comma;
    }

    public String toString(){
        return null;
    }
}
