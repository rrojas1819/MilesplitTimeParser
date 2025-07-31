package src;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import javax.swing.*;
import java.util.*;

public class FormattedParserRunner {
    public static List<Athlete> run(String url, String userInputTeam) throws Exception {
        WebDriver driver = CrossPlatformChrome.createHeadlessDriver();
        List<Athlete> athletes = new ArrayList<>();

        try {
            driver.get(url);
            Thread.sleep(3000);

            Document doc = Jsoup.parse(driver.getPageSource());
            Element resultsList = doc.getElementById("resultsList");
            if (resultsList == null)
                throw new Exception("No resultsList");

            Elements events = resultsList.getElementsByClass("eventResult");
            Set<String> teamNames = new TreeSet<>();
            for (Element event : events)
                for (Element row : event.select("tbody tr"))
                    teamNames.add(safeText(row.selectFirst("td.team")));

            String matchedTeam = findMatchingTeam(userInputTeam, teamNames);
            if (matchedTeam == null) {
                JOptionPane.showMessageDialog(null,
                        "No matching team: " + userInputTeam, "Error",
                        JOptionPane.ERROR_MESSAGE);
                return athletes;
            }

            Map<String, Map<String, Athlete>> eventToAthlete = new LinkedHashMap<>();
            int total = 0, matched = 0;

            for (Element event : events) {
                String eventName = safeText(event.selectFirst(".eventName"));
                Map<String, Athlete> map = eventToAthlete.computeIfAbsent(eventName, k -> new LinkedHashMap<>());

                for (Element row : event.select("tbody tr")) {
                    total++;
                    String name = safeText(row.selectFirst("td.athlete a"));
                    String mark = safeText(row.selectFirst("td.finish"));
                    String team = safeText(row.selectFirst("td.team"));

                    if (!team.equalsIgnoreCase(matchedTeam)) continue;

                    String key = (name + "|" + mark).toLowerCase();
                    if (map.containsKey(key)) continue;

                    Athlete a = new Athlete(name, mark, false);
                    a.setEvent(new String[]{eventName});
                    a.parseAndStoreTime(mark);
                    map.put(key, a);
                    matched++;
                }
            }

            for (Map<String, Athlete> map : eventToAthlete.values())
                athletes.addAll(map.values());

            Athlete summary = new Athlete("SUMMARY");
            summary.setTime("Team: " + userInputTeam + " | Unique: " + matched + " / Rows: " + total);
            athletes.add(summary);

            return athletes;
        } finally {
            driver.quit();
        }
    }

    private static String safeText(Element e) {
        return e == null ? "" : e.text().trim();
    }

    private static String findMatchingTeam(String input, Set<String> names) {
        input = input.toLowerCase();
        List<String> opts = new ArrayList<>();
        for (String t : names)
            if (t.toLowerCase().contains(input) || input.contains(t.toLowerCase()))
                opts.add(t);
        if (opts.size() == 1) return opts.get(0);
        if (opts.size() > 1) {
            Object choice = JOptionPane.showInputDialog(null,
                    "Choose Team", "Multiple matches",
                    JOptionPane.QUESTION_MESSAGE, null,
                    opts.toArray(), opts.get(0));
            return choice == null ? null : choice.toString();
        }
        return null;
    }
    public static String extractDefaultFilename(String url) {
        try {
            String[] parts = url.split("/meets/")[1].split("/results/");
            return parts[0];
        } catch (Exception e) {
            return "default_filename";
        }
    }

}
