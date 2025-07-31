package src;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserGUI {
    private static final List<Athlete> allResults = new ArrayList<>();
    private static boolean isDarkMode = false;
    private static String lastUsedUrl = "";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> buildUI("", "", isDarkMode));
    }

    private static void buildUI(String prevUrls, String prevSchool, boolean darkMode) {
        JFrame frame = new JFrame("Milesplit Formatted Parser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 730);
        frame.setLocationRelativeTo(null);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        frame.setContentPane(rootPanel);

        // Dark mode toggle top-right
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setOpaque(false);
        JCheckBox darkToggle = new JCheckBox("Dark Mode");
        darkToggle.setSelected(darkMode);
        darkToggle.setFocusPainted(false);
        darkToggle.setBorderPainted(false);
        darkToggle.setContentAreaFilled(false);
        darkToggle.setOpaque(false);
        darkToggle.addActionListener(e -> {
            isDarkMode = !isDarkMode;
            frame.dispose();
            buildUI(prevUrls, prevSchool, isDarkMode);
        });
        topBar.add(darkToggle, BorderLayout.EAST);
        rootPanel.add(topBar);

        // Centered title
        JLabel title = new JLabel("Milesplit Formatted Parser");
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.add(title);
        rootPanel.add(Box.createVerticalStrut(20));

        // Input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);
        inputPanel.setMaximumSize(new Dimension(500, 160));
        inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel schoolLabel = new JLabel("School Name:");
        schoolLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        schoolLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        schoolLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField schoolField = new JTextField();
        schoolField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        schoolField.setMaximumSize(new Dimension(500, 30));
        applyPlaceholder(schoolField, "Enter school name (e.g., Rahway)");

        JLabel urlLabel = new JLabel("Meet URLs (one per line):");
        urlLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        urlLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 4, 0));
        urlLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea urlArea = new JTextArea();
        urlArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        urlArea.setRows(4);
        urlArea.setLineWrap(true);
        urlArea.setWrapStyleWord(true);
        applyPlaceholder(urlArea, "Enter one or more Milesplit meet URLs (one per line)");
        JScrollPane urlScroll = new JScrollPane(urlArea);
        urlScroll.setMaximumSize(new Dimension(500, 80));

        inputPanel.add(schoolLabel);
        inputPanel.add(schoolField);
        inputPanel.add(urlLabel);
        inputPanel.add(urlScroll);
        rootPanel.add(inputPanel);
        rootPanel.add(Box.createVerticalStrut(20));

        // Actions
        JLabel actionsLabel = new JLabel("Actions");
        actionsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        actionsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.add(actionsLabel);
        rootPanel.add(Box.createVerticalStrut(5));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        JButton parseButton = new JButton("Parse All");
        JButton exportButton = new JButton("Export CSV");
        exportButton.setEnabled(false);

        for (JButton btn : new JButton[]{parseButton, exportButton}) {
            btn.setFont(new Font("SansSerif", Font.BOLD, 16));
            btn.setBackground(new Color(60, 120, 255));
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFocusable(false);
            btn.setBorder(new EmptyBorder(10, 25, 10, 25));
        }

        buttonPanel.add(parseButton);
        buttonPanel.add(exportButton);
        rootPanel.add(buttonPanel);
        rootPanel.add(Box.createVerticalStrut(20));

        // Output
        JLabel outputLabel = new JLabel("Output");
        outputLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        outputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rootPanel.add(outputLabel);
        rootPanel.add(Box.createVerticalStrut(5));

        JTextArea outputArea = new JTextArea();
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(780, 400));
        rootPanel.add(scrollPane);

        if (darkMode) {
            Color bg = new Color(30, 30, 30);
            Color fg = new Color(220, 220, 220);
            rootPanel.setBackground(bg);
            topBar.setBackground(bg);
            inputPanel.setBackground(bg);
            buttonPanel.setBackground(bg);
            scrollPane.getViewport().setBackground(bg);
            outputArea.setBackground(bg);
            outputArea.setForeground(fg);
            title.setForeground(fg);
            darkToggle.setForeground(fg);
            schoolLabel.setForeground(fg);
            urlLabel.setForeground(fg);
            actionsLabel.setForeground(fg);
            outputLabel.setForeground(fg);
            urlArea.setBackground(bg);
            urlArea.setForeground(fg);
            schoolField.setBackground(bg);
            schoolField.setForeground(fg);
        }

        parseButton.addActionListener((ActionEvent e) -> {
            String[] urls = urlArea.getText().split("\r?\n");
            String school = schoolField.getText().trim();
            outputArea.setText("");
            allResults.clear();
            exportButton.setEnabled(false);

            new Thread(() -> {
                for (String url : urls) {
                    url = url.trim();
                    if (url.isEmpty()) continue;
                    lastUsedUrl = url;
                    outputArea.append("Results from: " + url + "\n");
                    outputArea.append("========================================\n");
                    try {
                        List<Athlete> athletes = FormattedParserRunner.run(url, school);
                        for (Athlete a : athletes) {
                            allResults.add(a);
                            outputArea.append(a.toString());
                        }
                    } catch (Exception ex) {
                        outputArea.append("Error with URL: " + url + "\n");
                        writeErrorLog(ex);
                    }
                    outputArea.append("\n\n");
                }
                if (!allResults.isEmpty()) exportButton.setEnabled(true);
            }).start();
        });

        exportButton.addActionListener(e -> {
            String defaultFileName = "parsed_athletes.csv";
            if (!lastUsedUrl.isEmpty()) {
                try {
                    defaultFileName = FormattedParserRunner.extractDefaultFilename(lastUsedUrl) + ".csv";
                } catch (Exception ignored) {}
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new File(defaultFileName));
            if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                try (PrintWriter writer = new PrintWriter(chooser.getSelectedFile())) {
                    writer.println("Name,Type,Mark,Event");
                    for (Athlete a : allResults) {
                        String[] lines = a.toString().split("\n");
                        for (String line : lines) {
                            String[] parts = line.split("\t");
                            if (parts.length >= 4) {
                                writer.printf("\"%s\",%s,%s,\"%s\"\n", parts[0], parts[1], parts[2], parts[3]);
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(frame, "CSV exported.");
                } catch (IOException ex) {
                    writeErrorLog(ex);
                    JOptionPane.showMessageDialog(frame, "Failed to export.");
                }
            }
        });

        

        frame.setVisible(true);
    }

    private static void applyPlaceholder(JTextComponent field, String placeholderText) {
        Color placeholderColor = new Color(150, 150, 150); // gray
        Color normalColor = Color.BLACK;

        field.setText(placeholderText);
        field.setForeground(placeholderColor);

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(placeholderText)) {
                    field.setText("");
                    field.setForeground(normalColor);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholderText);
                    field.setForeground(placeholderColor);
                }
            }
        });
    }

    private static void writeErrorLog(Exception e) {
        try (PrintWriter pw = new PrintWriter(new FileWriter("error_log.txt", true))) {
            pw.println("[" + new java.util.Date() + "]");
            e.printStackTrace(pw);
            pw.println();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
