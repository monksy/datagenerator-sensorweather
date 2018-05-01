# Data Generator- Sensor Weather

The goal of this simple script/app is to generate a large set of data where it can show that sensors reported in a typical yearly fashion. (Starting cold at the beginning of each day and start of the year), peaking midway through, and falling back to where it started.

THis should generate a large enough dataset to work with big data systems. 

This allows for intraday variations and for yearly variations. Also, this produces unique patterns for each of the ids that you give it as well.

This is intented to produce a CSV file that can be directly injested into a database.

## How to compile
This is using the maven build system so

    mvn clean package 
    
This will produce a full jar, with all of it's dependencies in target. 

## Example Run

     java -jar datagenerator-sensorweather-1.0-SNAPSHOT-allinone.jar a,b,c,d,e,f 3000 6000 

## Inputs 

| Parameter | Description | Example |
| --------- | ----------- | ------- |
| ids       | The ids for the temperature probes to report back on | a,b,c | 
| every-seconds | For every one of these seconds of the day, a report will be made from each probe | 30 (every thirty seconds of the day a report will be made) | 
| days | This is for how many days that reports will be made | 30 (30 days of reports + intraday reports will be made) | 


## Output 

A CSV output will come out of standard out ready to be consumed. 

### Example

    checkintime,id,temperature
    1525179480523,a,69.54
    1525209480523,a,81.59
    1525239480523,a,69.54
    1525179480523,b,65.47
    1525209480523,b,82.06
    1525239480523,b,65.47
    1525265880523,a,67.71
    1525295880523,a,85.04
    1525325880523,a,67.71
    1525265880523,b,67.10
    1525295880523,b,81.94
    1525325880523,b,67.10


## SQL Table to import the data into 

Note: The timestamp type will not accept epoch. 

    create table sesnordata
    (
      checkintime bigint not null,
      sensor varchar(10) not null,
      temperature double precision
    );
    

## Improvements that could be made

 * Refactoring for clarity 
 * Injection of occasional bad data (to simulate bad sensors)
 * Configurability to show when a sensor may have failed to report in
 * Better date handling to start at the beginning of the year rather than the current day.

