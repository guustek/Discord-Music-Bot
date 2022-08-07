heroku ps:scale worker=1
worker: java -agentlib:jdwp=transport=dt_socket,server=y,address=9090,suspend=n -jar ./target/PopusBot.jar
heroku ps:forward 9090
