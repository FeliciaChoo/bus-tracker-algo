#!/bin/bash

mongoimport --uri "mongodb://localhost:27017/bustracker" \
  --collection stops \
  --jsonArray \
  --file ./stops.json

mongoimport --uri "mongodb://localhost:27017/bustracker" \
  --collection dublinked_data \
  --jsonArray \
  --file ./traces.json
