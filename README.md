# zonky-client

Simple Spring Boot application which every 5 minutes check Zonky loan marketplace for new loans and displays it.

Spring Boot was chosen due to good tools for testing, scheduling and REST requesting.
And because of small memory requirements.

Run it from command line: `./gradlew bootRun` in root directory of the project.
 
 Alternative way: compile executable jar by
 `./gradlew bootJar && chmod a+x ./build/libs/zonky-client-*.jar`
  and then run it: `./build/libs/zonky-client-*.jar`  