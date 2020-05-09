package utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import flow.Main;
import logic.Release;

public class DataStructures {
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	private DataStructures() {
	      //not called
	   }
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try (BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
			String jsonText = readAll(rd);
			return new JSONObject(jsonText);
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static void insertInCsv(Release rel, String pathCsv) {
		Integer numVersions = rel.getReleases().size();
		if (numVersions < 6)
			return;
		FileWriter fileWriter = null;
		try {
			fileWriter = null;
			//Name of CSV for output
			fileWriter = new FileWriter(pathCsv);
			fileWriter.append("Index,Version ID,Version Name,Date");
			fileWriter.append("\n");
			for (Integer i = 0; i < numVersions; i++) {
				Integer index = i + 1;
				fileWriter.append(index.toString());
				fileWriter.append(",");
				fileWriter.append(rel.getReleaseID().get(rel.getReleases().get(i)));
				fileWriter.append(",");
				fileWriter.append(rel.getReleaseNames().get(rel.getReleases().get(i)));
				fileWriter.append(",");
				fileWriter.append(rel.getReleases().get(i).toString());
				fileWriter.append("\n");
			}

		} catch (Exception e) {
			LOGGER.warning("Error in csv writer"+e.getMessage());
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				LOGGER.warning("Error while flushing/closing fileWriter !!!"+e.getMessage());
			}
		}
	}
}
