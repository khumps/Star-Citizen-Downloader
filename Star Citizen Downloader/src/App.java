public class App {

	public static void main(String[] args) {
		System.out.println("Thankyou for using the Star Citizen Downloader, created by khumps");
		System.out.println("The game, name and all assets are sole property of Cloud Imperium Games");
		System.out.println("The downloader comes as is, report any downloader issues to the developer");
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Downloader.cleanup(); // Cleans up any temp files from this run at the end
		}));
		if (args.length < 1 && args[1] != null)
			if (args[1].equals("debug")) {
				Downloader.debug = true;
				System.err.println("DOWNLOADER IS RUNNING IN DEBUG MODE, VERBOSE LOGGING ENABLED");
			}
		Downloader.cleanup(); // Cleans up any left over files from the last run that might have been missed
		Downloader.getLatest(); // Gets latest file lists
		switch (args[0].toLowerCase()) {
		case "public":
			Downloader.getPublic();
			break;
		case "test":
			Downloader.getTest();
			break;
		case "both":
			Downloader.getPublic();
			Downloader.getTest();
			break;
		default:
			System.err.println("Invalid argument, please use public, test, or both");
		}
	}
}
