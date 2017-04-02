public class GameDownloader {

	public static void main(String[] args) {
		System.out.println("Thankyou for using the Star Citizen Downloader, created by khumps");
		System.out.println("The game, name and all assets are sole property of Cloud Imperium Games");
		System.out.println("The downloader comes as is, report any downloader issues to the developer");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			Manifest.cleanup();
		}));
		if (args.length < 1 && args[1] != null)
			if (args[1].equals("debug")) {
				Manifest.debug = true;
				System.err.println("DOWNLOADER IS RUNNING IN DEBUG MODE, VERBOSE LOGGING ENABLED");
			}
		Manifest.cleanup();
		switch (args[0].toLowerCase()) {
		case "public":
			Manifest.getPublic();
			break;
		case "test":
			Manifest.getTest();
			break;
		case "both":
			Manifest.getPublic();
			Manifest.getTest();
			break;
		default:
			System.err.println("Invalid argument, please use public, test, or both");
		}
	}
}
