# Star-Citizen-Downloader
A downloader I created for people having issues downloading Star Citizen using the official launcher
This launcher is in no way officially supported and is provided as is.
Clound Imperium Games owns all rights to all content of the files that are downloaded using this launcher.

Download it [here](https://github.com/khumps/Star-Citizen-Downloader/raw/master/StarCitizenDownloader.jar)

# Running it(Admin command prompt)

1. move the installer into your CIG directory(normally located at C:\Program Files\Clound Imperium Games). Put it in the ROOT of that folder
1. Open an admin command prompt(Windows+X and click command prompt(admin))
2. navigate to that same directory (if you don't know how to do that then the other tutorial is better for you ;) remove any StarCitizen forlder if it exists
3. run the program (java -jar whatever-you-named-the-jar.jar public|test|both)
4. wait for it to finish.
5. open the launcher and login
6. if the launcher says update, close the launcher
7. navigate to the patcher folder in the CIG directory and open up the PatcherState file in a text editor
8. look for the lines Public_downloadFinished and/or Test_downloadFinished and change false to true
9. reopen the launcher, you should now be able to run the game.
10. enjoy!


# Running it(no admin command prompt)
1. move the installer to your desktop
2. shift+right click on your desktop. a diaglog should pop up with the option to open command prompt here.
3. run the program (java -jar whatever-you-named-the-jar.jar public|test|both)
4. wait for it to finish.
6. copy the StarCitizen directory from your desktop to your CIG directory(normally located at C:\Program Files\Clound Imperium Games) delete any existing StarCitizen folder first
5. open the launcher and login
6. if the launcher says update, close the launcher
7. navigate to the patcher folder in the CIG directory and open up the PatcherState file in a text editor
8. look for the lines Public_downloadFinished and/or Test_downloadFinished(whichever you downloaded) and change false to true
9. reopen the launcher, you should now be able to run the game.
10. enjoy!


# Please report any issues you have in the issue tracker. Thanks!
