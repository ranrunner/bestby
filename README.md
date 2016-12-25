#Best By (Android app)
BestBy is an Android app started as a project for my personal portfolio.<br /><br />
The goal of the app is to:<br />
* store food items and their best by dates
* have items be added to and removed from a SQLite database<br /><br />

The user interacts with:<br />
* a scrollable list view that shows each item with its best by date
* activities (screens) that lets the user add and remove items<br /><br />

Java source files
-----------------
<b>Follow path to see these files:</b> <i>/bestby/app/src/main/java/com/ranrunner/bestby/</i>
* <b>AddActivity.java</b> -- this supports the activity that lets the user add an item
* <b>DatabaseHandler.java</b> -- this specifies properties for and creates the database, with supporting queries
* <b>DatePickerFragment.java</b> -- this enables a date picker for date selection
* <b>Item.java</b> -- this is a class that holds a single entry from the database (item ID, item, and date)
* <b>ListActivity.java</b> -- this supports the main activity (main screen with scrollable list)
* <b>ListCursorAdapter.java</b> -- this enables the user to see a populated list view
* <b>RemoveActivity.java</b> -- this supports the activity that lets the user remove an item<br /><br />

Layout resource files
---------------------
These are XML files that specify the UI for each activity.<br /> <br />
<b>Follow path to see these files:</b> <i>/bestby/app/src/main/res/layout/</i>
* <b>activity_add.xml</b> -- linked with AddActivity.java
* <b>activity_list.xml</b> -- linked with ListActivity.java
* <b>activity_remove.xml</b> -- linked with RemoveActivity.java
* <b>list_item.xml</b> -- fragment that specifies the layout for each element in the list view<br /><br />

String values
-------------
Every string on each layout is stored as a string resource in <i>strings.xml</i><br />
<b>Follow path to see this file:</b> <i>/bestby/app/src/main/res/values/</i>

Future implementation
---------------------
* <b>App options</b>
* <b>Notifications</b>
* <b>Launcher widget</b>
