package flow;

import java.io.IOException;

import org.json.JSONException;

import logic.Release;
import utils.DataStructures;

public class Main {
	private static final String PROJNAME ="SAMZA";
	private static final String PATHCSV = PROJNAME + "VersionInfo.csv";

	public static void main(String[] args) throws JSONException, IOException {
		Release rel = new Release(PROJNAME);
		
		DataStructures.insertInCsv(rel, PATHCSV);
	}
}
