#!/bin/bash
# this sh is needed to run the program

cd  $HOME/Desktop/Duolingo/src
javac -cp .:libs/selenium-server-4.8.3.jar Main.java
java -cp .:libs/selenium-server-4.8.3.jar Main
