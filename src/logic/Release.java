package logic;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.DataStructures;

public class Release {
	private HashMap<LocalDateTime, String> releaseNames;
	private HashMap<LocalDateTime, String> releaseID;
	private ArrayList<LocalDateTime> releases;
	private Integer numVersions;

	public Release(String projName) throws JSONException, IOException {
		//Fills the arraylist with releases dates and orders them
		//Ignores releases with missing dates
		releases = new ArrayList<>();
		Integer i;
		String url = "https://issues.apache.org/jira/rest/api/2/project/" + projName;
		JSONObject json = DataStructures.readJsonFromUrl(url);
		JSONArray versions = json.getJSONArray("versions");
		releaseNames = new HashMap<>();
		releaseID = new HashMap<> ();
		for (i = 0; i < versions.length(); i++ ) {
			String name = "";
			String id = "";
			if(versions.getJSONObject(i).has("releaseDate")) {
				if (versions.getJSONObject(i).has("name"))
					name = versions.getJSONObject(i).get("name").toString();
				if (versions.getJSONObject(i).has("id"))
					id = versions.getJSONObject(i).get("id").toString();
				addRelease(versions.getJSONObject(i).get("releaseDate").toString(), name, id);
			}
		}
		// order releases by date
		Collections.sort(releases, new Comparator<LocalDateTime>(){
			//@Override
			public int compare(LocalDateTime o1, LocalDateTime o2) {
				return o1.compareTo(o2);
			}
		});
		numVersions = releases.size();
	}

	public void addRelease(String strDate, String name, String id) {
		LocalDate date = LocalDate.parse(strDate);
		LocalDateTime dateTime = date.atStartOfDay();
		if (!releases.contains(dateTime))
			releases.add(dateTime);
		releaseNames.put(dateTime, name);
		releaseID.put(dateTime, id);
	}

	public HashMap<LocalDateTime, String> getReleaseNames() {
		return releaseNames;
	}

	public HashMap<LocalDateTime, String> getReleaseID() {
		return releaseID;
	}

	public ArrayList<LocalDateTime> getReleases() {
		return releases;
	}

	public Integer getNumVersions() {
		return numVersions;
	}
}
