Group Project - README Template
===

# Humboldt Tourism App

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
A hub for tourists, new students, and anyone who may be unfamiliar with the area to find popular sites and activities in the Humboldt area.

### App Evaluation

- **Category: Travel/Tourism**
- **Mobile: Find locations on map within the app. Take pictures from device, post to app. Communicate with other people using this app**
- **Story: Clear value to incoming freshman, transfer students, potential new students and visiting family and friends of students**
- **Market: Incoming/Potential new students and visiting  families**
- **Habit: App is more informative than social driven. The user will access the app when they want to know about their surroundings**
- **Scope: There is many functionalities that can be added to the stripped down version of this app. However, a stripped down version of the app is still very useful and informative**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* Events from around Humboldt County should be obtained via API or similar source and displayed to user
* Events from Humboldt State University should be obtained via API or similar source and displayed to user
* Detailed page for a specific event can be viewed when event has been selected.
* Map page for connecting events to locations

**Optional Nice-to-have Stories**

* User can take and upload events/images within the app
* Users can interact with eachother via app for meet-ups/group activities.

### 2. Screen Archetypes

* Home Timeline
   * A click on a specific event leads to a detailed view of the event.
   * ...
* Humboldt State University Events Timeline
   * A click on a specific event leads to a detailed view of the event.
   * ...
* Events Map
   * A click on a pinned event within the map view leads to a detailed view of the event.
   * ...
* Drawer Menu
   * A click on an item in the menu leads to the fragment associated with that item (Timeline, Map, etc.).
   * ...

### 3. Navigation

**Tab Navigation**

<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Tab%20Navigation.JPG" width=400>

**Flow Navigation** (Screen to Screen)

<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Flow%20Navigation.JPG" width=400>

### Walkthrough (unit 10)
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/walkthroughunit10.gif" width=400>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Home%20Timeline.JPG" width=400>
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/HSU%20Events%20Timeline.JPG" width=400>
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Detailed%20Event%20View.JPG" width=400>
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Events%20Map.JPG" width=400>
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/Drawer%20Menu.JPG" width=400>

### [BONUS] Interactive Prototype
<img src="https://github.com/humboldt-cs/cs480-hum-spots/blob/master/wireframes/interactivePrototype.gif" width=400>

## Schema
### Models
#### Event

   | Property      | Type     | Description |
   | ------------- | -------- | ------------|
   | eventId       | String   | unique id for the event (default field) |
   | author        | Pointer to User| image author |
   | image(s)      | File(s)  | poster/image(s) about the event|
   | eventDate     | DateTime | date when event is to take place |
   | eventTitle    | String   | name of event |
   | eventDescription | String | Description of event |
   | eventMapLocation | Map Coordinates | Location on map where event is to take place |
   | eventVenue    | String | Arena/Venue where event is to take place |

### Networking
#### List of network requests by screen
- Home Timeline
      - (Read/GET) Query all events where user is author
         ```swift
         
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(TEST_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
           // TODO: Do something with posts...
            }
         }
         ```

#### [OPTIONAL:] Existing API Endpoints
##### Eventbrite
- Base URL - [https://www.eventbriteapi.com/v3](https://www.eventbriteapi.com/v3)

   HTTP Verb | Endpoint | Description
   ----------|----------|------------
    `GET`    | venues/venue_id/events/ | get events by venue
    `GET`    | organizations/organization_id/events/ | get events by organization
    `GET`    | series/event_series_id/events/  | get event by event series
   
