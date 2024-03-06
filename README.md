# Take-home software development task

As part of our developer interview process, we are asking you to complete this small programming project. We
appreciate your <me, we designed the task to be simple, so that you won't have to spend too much <me on it. All
instruc<ons are provided in this document, but you can of course reach out to us if you have any ques<ons.
Since we are primarily focused on backend development within our team this task is similar to what our developers work
on regularly. We design and implement our own APIs and communicate with external APIs all the <me, so that's what we
are asking you to do here.
Places of interest near close to the ISS
We are asking you to create an app that will interact with two publicly available APIs to provide informa<on about places
of interest near where the Interna<onal Space Sta<on (ISS) is currently flying over.
The applica<on should accept an HTTP GET request and respond with a list of closest places that are tagged near the
current loca<on of the ISS. The response should be a JSON and should include the loca<on coordinates (la<tude and
longitude), <tle (name) and country of each place. The list should display the 10 closest places of interest found. If there
are fewer than 10 places found the list can be shorter.

## Example output JSON:

```
{
  "results": [
    {
      "title": "Place Name 1",
      "latitude": 37.78916,
      "longitude": -122.61458,
      "country": "Canada"
    },
    {
      "title": "Place Name 2",
      "latitude": 37.78916,
      "longitude": -122.61458,
      "country": "Canada"
    }
  ]
}
```

These free public APIs provide all the required information. 
You’ll need to read the API documentation to figure out how to use them and obtain the relevant information:
* Open Notify API - International Space Station Current Location
* API documenta<on can be found here: hWp://open-notify.org/Open-No<fy-API/ISS-Location-Now/
* This is a free API that has a module that provides the real <me coordinates of where the ISS is overflying.
* MediaWiki Action API (Wikipedia)
* API documentation can be found here: hWps://www.mediawiki.org/wiki/API:Main_page
* This is the official Wikipedia API. It is very powerful and can give you access to all sorts of information.
The API includes func<onality that provides information about wiki ar<cles tagged near any given
coordinates.

## Requirements:

* The application will run an HTTP server accepting GET requests.
* You can use any programming language and/or framework.
* You are free to import and use any open-source library or framework in your application. If any part of your
solution is readily available do not hesitate to use it in your implementation.
* You don't need to cover every single corner case, but the application should handle any obvious error cases
gracefully.
* The applica<on should be dockerized (ie. You need to create a Dockerfile that would be used to create a docker
image for your applica<on)
