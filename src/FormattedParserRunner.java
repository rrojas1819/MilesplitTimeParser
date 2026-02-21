package src;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;
import java.util.*;

public class FormattedParserRunner {

    /** Parses one URL using an existing driver. Does not create or quit the driver. */
    public static List<Athlete> runWithDriver(WebDriver driver, String url, String userInputTeam) throws Exception {
        getWithRetry(driver, url, 3);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("resultsList")));

        Document doc = Jsoup.parse(driver.getPageSource());
        Element resultsList = doc.getElementById("resultsList");
        if (resultsList == null)
            throw new Exception("No resultsList");

        return parseResultsDocument(doc, userInputTeam);
    }

    /** Shared parsing logic: document must already contain #resultsList. */
    private static List<Athlete> parseResultsDocument(Document doc, String userInputTeam) {
        List<Athlete> athletes = new ArrayList<>();
        Element resultsList = doc.getElementById("resultsList");
        if (resultsList == null)
            return athletes;

        Elements events = resultsList.getElementsByClass("eventResult");
        Set<String> teamNames = new TreeSet<>();
        for (Element event : events)
            for (Element row : event.select("tbody tr")) {
                String team = teamTextFromRow(row);
                if (!team.isEmpty()) teamNames.add(team);
            }

        String matchedTeam = findMatchingTeam(userInputTeam, teamNames);
        if (matchedTeam == null) {
            String detail;
            if (teamNames.isEmpty()) {
                detail = "No team names found in results (page may need browser).";
            } else {
                List<String> list = new ArrayList<>(teamNames);
                String joined = list.size() <= 15
                        ? String.join(", ", list)
                        : String.join(", ", list.subList(0, 15)) + " ... and " + (list.size() - 15) + " more";
                detail = "Teams found: " + joined;
            }
            JOptionPane.showMessageDialog(null,
                    "No matching team: " + userInputTeam + "\n\n" + detail, "Error",
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
                String team = teamTextFromRow(row);

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
    }

    /** Creates a new driver, parses one URL, then quits. Use runWithDriver when parsing multiple URLs. */
    public static List<Athlete> run(String url, String userInputTeam) throws Exception {
        WebDriver driver = CrossPlatformChrome.createHeadlessDriver();
        try {
            return runWithDriver(driver, url, userInputTeam);
        } finally {
            driver.quit();
        }
    }

    private static void getWithRetry(WebDriver driver, String url, int maxAttempts) throws Exception {
        Exception last = null;
        for (int i = 0; i < maxAttempts; i++) {
            try {
                driver.get(url);
                return;
            } catch (Exception e) {
                last = e;
                if (i < maxAttempts - 1) {
                    Thread.sleep(2000);
                }
            }
        }
        throw last != null ? last : new Exception("Failed to load " + url);
    }

    private static String safeText(Element e) {
        return e == null ? "" : e.text().trim();
    }

    /** Team name from row: prefer link text in td.team (matches Milesplit formatted HTML). */
    private static String teamTextFromRow(Element row) {
        Element tdTeam = row.selectFirst("td.team");
        if (tdTeam == null) return "";
        Element link = tdTeam.selectFirst("a");
        return link != null ? safeText(link) : safeText(tdTeam);
    }

    private static String findMatchingTeam(String input, Set<String> names) {
        if (input == null) input = "";
        input = input.trim().toLowerCase();
        if (input.isEmpty()) return null;
        List<String> opts = new ArrayList<>();
        for (String t : names) {
            String tl = t.trim().toLowerCase();
            if (tl.equals(input) || tl.contains(input) || input.contains(tl))
                opts.add(t);
        }
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
