package flow;

import java.io.IOException;
//import java.util.logging.Logger;

import org.json.JSONException;

import logic.Release;
import utils.DataStructures;

public class Main {
	//private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	private static final String PROJNAME ="SAMZA";
	private static final String PATHCSV = PROJNAME + "VersionInfo.csv";

	public static void main(String[] args) throws JSONException, IOException {
		Release rel = new Release(PROJNAME);
		
		DataStructures.insertInCsv(rel, PATHCSV);
	}
}
