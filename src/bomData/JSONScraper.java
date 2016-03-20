package bomData;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

public class JSONScraper {
	
	public static void main(String[] args) throws IOException 
	{
		scrapeLocations();
	}
	
	public static Vector<Location> scrapeLocations() throws IOException {
		// RMIT Proxy Settings
		// System.setProperty("http.proxyHost", "aproxy.rmit.edu.au");
		// System.setProperty("http.proxyPort", "8080");
		Vector<Location> locations = new Vector<Location>();
		String[] states = {"vic", "nsw", "tas", "wa", "sa", "nt", "qld", "ant"};
		String relURL;
		
		for(String state: states) {
			Document doc = Jsoup.connect("http://www.bom.gov.au/" + state + "/observations/" + state + "all.shtml").get();
			Elements tbodies = doc.select("tbody");
			Elements links = tbodies.select("a");
			for (Element link: links) {
				relURL = link.attr("href");
				if (relURL.contains("products") && !relURL.contains("#")) {
					relURL = "http://www.bom.gov.au" + relURL.replace("products", "fwo").replace("shtml", "json");
					JsonArray rootArray = new JsonParser().parse(new BufferedReader(
							new InputStreamReader(new URL(relURL).openStream())))
						.getAsJsonObject().getAsJsonObject("observations").getAsJsonArray("header");
					
					//locations.add(new Location()) link.text();
				}			
			}
		}
		return locations;
	}
}
