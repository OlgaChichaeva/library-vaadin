library-vaadin
==============

Simple Vaadin application.



Build and Run
-------------

1. Run in the command line:
 
 mvn package
 mvn jetty:run
 

2. Open `http://localhost:8080` in a web browser.
If Vaadin pre-releases are not enabled by default, use the Maven parameter
"-P vaadin-prerelease" or change the activation default value of the profile in pom.xml .
