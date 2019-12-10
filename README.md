# bus-tracker

## About Bus Tracker API

An API that provides endpoints to retrieve data from a Dublin Bus GPS sample data in DUBLinked Metadata Standard.

## Requirements

* JDK 8
* Maven 3
* MongoDB 4.0

or

* Docker
* Docker Compose

## How to run locally

Download a CSV from this URL:

  * https://data.gov.ie/dataset/dublin-bus-gps-sample-data-from-dublin-city-council-insight-project

Import a CSV file to a MongoDB with:

 * Database name: `busTracker` 
 * Collection name: `dublinBusGPSSample`
 * By default, MongoDB must be running at `localhost:27017`.

Git clone, build and run:

```sh
git clone git@github.com:paulohva/bus-tracker.git
```
```sh
./mvn install
```
```sh
./mvn spring-boot:run
```

## How to run using Docker Compose

Git clone the repository:

```sh
git clone git@github.com:paulohva/bus-tracker.git
```

Set execute permission to the import script:

```sh
chmod +x mongo-seed/import.sh
```

Download a CSV from this URL:

```url
https://data.gov.ie/dataset/dublin-bus-gps-sample-data-from-dublin-city-council-insight-project
```

Rename a CSV file to `dublin-bus-sample.csv` and put into the `mongo-seed` folder.

Run Docker Compose command from the project root folder:

```sh
./docker-compose up --build
```

## API specifification

The API documentation was made in using Swagger. After doing the build&run steps, use the link bellow:

* http://localhost:8080/v1/swagger-ui.html

## Testing the API

Use a tool that can create HTTP requests, like Postman (https://www.getpostman.com/)