# Statistics Calculator

A statistics calculator that processes and incoming stream of events and builds required statistics.

The incoming stream of events is pushing to the statistics calculator by invoking the “event”
method. The events are sent by multiple-threads. so the implementation is thread-safe.

###Tech Stack
* Java8
* SprigBoot
* maven

###Assumptions
* It can store upto last 60 minutes of stream data. When new data will be added older will be removed automatically.
* Memory : 200 MB min

###Steps to Run
* Build : execute 'mvn clean install'
* Run : execute 'java -jar calculator-0.0.1-SNAPSHOT.jar'
