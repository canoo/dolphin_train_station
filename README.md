#Open Dolphin Demo: arrival/departure board and admin gui for a train station

This application is about a train station, say Olten in Switzerland.
On the platforms of the station there are many departure boards informing people about
trains starting from this station in the near future.

Such a departure board will be displayed in this demo in form of a window which a table showing the next
5 trains.

In addition this application contains a so called admin GUI to maintain information about trains and
simulate arrival and departures of trains.

##Build and run from commandline
* ./gradlew build
* ./gradlew :client:installApp
* cp client/build/install/client/bin startadmin
* cd client/build/install/client/bin
* mv client startadmin
* cp startadmin startboard
* change classname in the last line in startboard script from 'AdminApplicationStarter' to 'DisplayApplicationStarter'
* open a second terminal in the project directory and start the server by invoking: ./gradlew jettyRun
* in the first terminal start one instance (as background process so that you can reuse the terminal) of the board client by invoking: ./startboard &
  which should bring up a GUI with an empty table
* start a seconde instance of the board in the same way to simulate 2 boards on the station's platform
* now start the admin GUI: ./startadmin &




##Build and run from IntelliJ Idea


##Build

##Run





