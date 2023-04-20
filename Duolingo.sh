#!/bin/bash
# this sh is needed to run the program

cd  $HOME/Desktop/Duolingo/
mvn clean compile exec:java -Dexec.mainClass="org.duolingo.Main"
