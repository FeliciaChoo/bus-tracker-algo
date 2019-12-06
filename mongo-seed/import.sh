#!/bin/bash
if [ ! -f "/mongo-seed/dublin-bus-sample.csv" ]; then
  echo "The file 'dublin-bus-sample.csv' does not exist. Skiping mongo-seed task."
else
  mongoimport --host=mongo -d busTracker -c dublinBusGPSSample --type csv --file /mongo-seed/dublin-bus-sample.csv --fields timestamp,lineID,direction,journeyPatternID,timeFrame,vehicleJourneyID,operator,congestion,lonWGS84,latWGS84,delay,blockID,vehicleID,stopID,atStop
fi
