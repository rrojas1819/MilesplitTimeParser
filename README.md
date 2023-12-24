MileSplit time and event Parser

Have a working version so far in the InputData branch, however still want to check and fix a few things.

More importantly this project's function is to parse for names and times/distances of athletes and copy them and strucuting them into a table format to then paste into Excel or Google Sheets.


The program asks for three things before it can automically do it, and they are the 
**URL** of the **meet** _(which has to be in the completed format as of now.)_
**School name** _(The beginning of the name, EX: Rahway HS => Rahway)_

Lastly
**Checkbox** _(That asks you to check if a name has a comma | this is because names with commas tend to start with last name and then first name EX: Rojas, Richie would mean to click on the checkbox!)_


Plans as of right:
- To include a GUI pop up showing a table of the collected athlete info as well as telling the individual the the information has been copied successfully
- Need validation and pop up to show if things are incorrect or not found.
- Eventually redesign the structure of the TrackProgram as while it is functional, it could be shorten greatly as there are many if statements that occur that can be simplified!
- _Future plan is to be a website version of this application!_
