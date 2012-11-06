storm-election-example
======================

This is a simple demo app based on the storm-starter project (https://github.com/nathanmarz/storm-starter.) that monitors twitter for certain keywords related to the GOP primary, displays the latest tweets, generats a tag cloud and graphs the number of mentions each candidate has received. 

A video of the app running can be found here: http://www.youtube.com/watch?v=pfwyP0vUqew

How to run this example:

Using Leiningen (https://github.com/technomancy/leiningen)

lein deps 
lein compile
java -cp `lein classpath` storm.starter.TwitterTopology

redis-server

node app.js




