import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program{
    public static void main(String[] args) {
        // Specify the path to your HTML file
        String filePath = "C:\\Users\\ariharan.s\\Downloads\\index.html";
        String htmlContent = readHtmlFile(filePath);

        // Parse HTML content using JSoup
        Document document = Jsoup.parse(htmlContent);

        // Select all list items (li) within the HTML content
        Elements listItems = document.select("li");

        // Create a JSON array to store the capitals
        JSONArray capitalsArray = new JSONArray();

        // Process each list item
        for (Element listItem : listItems) {
            // Extract capital and state from the list item
            String capital = listItem.select(".capital").text().trim();
            String state = listItem.select(".state").text().trim();

            // Create a JSON object for each state-capital pair
            JSONObject capitalObject = new JSONObject();
            capitalObject.put("capital", capital);
            capitalObject.put("state", state);

            // Add the state-capital pair to the array
            capitalsArray.put(capitalObject);
        }

        // Create the "summary" object
        JSONObject summaryObject = new JSONObject();
        summaryObject.put("numberOfCapitals", capitalsArray.length());

        // Create the outer JSON object
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("capitals", capitalsArray);
        jsonObject.put("summary", summaryObject);

        // Output the JSON to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.json"))) {
            writer.write(jsonObject.toString(4)); // Use toString(4) for pretty-printing with indentation
            System.out.println("Output written to output.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readHtmlFile(String filePath) {
        try {
            byte[] encodedBytes = Files.readAllBytes(Paths.get(filePath));
            return new String(encodedBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
