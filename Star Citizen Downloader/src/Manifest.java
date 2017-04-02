import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class Manifest {
	public static boolean debug = false;
	public static final String LAUNCHER = "launcher.txt";
	public static String publicIndex = null;
	public static String testIndex = null;

	private static void downloadFiles(String manifest, String baseFolder) {
		try (Scanner s = new Scanner(new File(manifest))) {
			String prefix = null;
			if (s.hasNextLine()) {
				String st = s.nextLine();
				prefix = st.substring(0, st.indexOf("StarCitizen"));
			}

			int pLength = prefix.length() + 11;
			while (s.hasNextLine()) {
				String st = s.nextLine();
				downloadFile(st, "StarCitizen/" + baseFolder + st.substring(pLength));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void downloadFile(String src, String destPath) {
		System.out.println("Downloading " + destPath);
		try {
			URL website = new URL(src);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			File temp = new File(destPath);
			if (temp.getParentFile() != null)
				temp.getParentFile().mkdirs();
			else
				temp.createNewFile();
			temp.createNewFile();
			fos = new FileOutputStream(temp);
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void getLatest() {
		System.out.println("Pulling latest file lists from RSI...");
		downloadFile("http://manifest.robertsspaceindustries.com/Launcher/_LauncherInfo", LAUNCHER);
		try (Scanner s = new Scanner(new File(LAUNCHER))) {
			String pub = null;
			String test = null;
			while (s.hasNextLine()) {
				String st = s.nextLine();
				if (st.contains("Public_fileIndex"))
					pub = st.substring(19).trim();
				if (st.contains("Test_fileIndex"))
					test = st.substring(17);
			}
			System.out.println("Downloading indexes... ");
			downloadFile(pub, "publicIndex.txt");
			downloadFile(test, "testIndex.txt");
			System.out.println("Indexes complete");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void getTest() {
		System.out.println("Downloading the lastest test version(PTU) of the game...");
		if (testIndex == null)
			getLatest();
		generateManifest("testIndex.txt");
		downloadFiles("manifest_testIndex.txt", "Test");
	}

	public static void getPublic() {
		System.out.println("Downloading the latest public version of the game...");
		if (publicIndex == null)
			getLatest();
		generateManifest("publicIndex.txt");
		downloadFiles("manifest_publicIndex.txt", "Public");
	}

	private static void generateManifest(String fileIndex) {
		System.out.println("Creating manifest for: " + fileIndex);
		ArrayList<String> files = new ArrayList<String>();
		String prefix = null;
		String seed = null;
		try (Scanner s = new Scanner(new File(fileIndex)); PrintWriter w = new PrintWriter("manifest_" + fileIndex)) {

			// Find files
			while (s.hasNextLine()) {
				String st = s.nextLine();
				if (st.contains("file_list"))
					break;
			}
			// Read files
			while (s.hasNextLine()) {
				String st = s.nextLine();
				if (st.contains("]"))
					break;
				String str = st.trim().replaceAll("\"", "").replaceAll(",", "");
				files.add(str);
			}
			// Find Prefix
			while (s.hasNextLine()) {
				String st = s.nextLine();
				if (st.contains("key_prefix")) {
					prefix = st.trim().replaceAll("\"", "").replaceAll(",", "").substring(12);
					if (debug)
						System.out.println("Found prefix: " + prefix);
					break;
				}
			}
			// Find Seed
			while (s.hasNextLine()) {
				String st = s.nextLine();
				if (st.contains("http")) {
					seed = st.trim().replaceAll("\"", "").replaceAll(",", "");
					if (debug)
						System.out.println("Found seed: " + seed);
					break;
				}
			}
			if (seed == null || prefix == null) {
				System.out.println("Seed or Prefix is null. Please try again, if it persists, report to the developer");
				System.exit(-1);
			}
			for (String st : files) {
				String str = seed + "/" + prefix + "/" + st;
				w.println(str);
				if (debug)
					System.out.println("File: " + str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void cleanup() {
		File launcher = new File("launcher.txt");
		File publicIndex = new File("publicIndex.txt");
		File testIndex = new File("testIndex.txt");
		File manPublic = new File("manifest_publicIndex.txt");
		File manTest = new File("manifest_testIndext.txt");
		launcher.delete();
		publicIndex.delete();
		testIndex.delete();
		manPublic.delete();
		manTest.delete();

	}
}
