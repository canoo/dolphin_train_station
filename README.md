#Open Dolphin Demo: arrival/departure board and admin gui for a train station

Created at Canoo Code Camp, Spring of 2013

This application is about a train station, say Olten in Switzerland.
On the platforms of the station there are many departure boards informing people about
trains starting from this station in the near future.

Such a departure board will be displayed in this demo in form of a window which a table showing the next
5 trains.

In addition this application contains a so called admin GUI to maintain information about trains and
simulate arrival and departures of trains.

For a more extensive description of the concepts, please see this blog post:
http://www.canoo.com/blog/2013/04/17/dolphin-train-station/

##Build and run from commandline  WINDOWS OS
* gradlew clean build install
* open a terminal in the project directory and start the server by invoking: 'gradlew jettyRun'
* open a second terminal in the project directory and start all clients at once by invoking: 'startclients.cmd'


##Build and run from commandline  LINUX OS
* ./gradlew clean build install
* open a terminal in the project directory and start the server by invoking: ./gradlew jettyRun
* open a second terminal in the project directory and start all clients at once by invoking: './startclients.sh'

The admin GUI is a master/detail view which displays all trains in a table. Selecting one entry shows the details
of it in an editor in the right part of the window. When editing details not how the data in the table changes as well.

Now select an entry in the table and press the button 'First entry in departure board' which will send five records
starting from the selected one to the boards. Note that they appear on the boards. And now if you change data
of one of those 5 records in the editor the data changes immediately not only in the table of the admin GUI but
also on the departure boards.
