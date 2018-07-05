[![Build Status](https://travis-ci.org/Tweetsched/tweetsched-cron.svg?branch=master)](https://travis-ci.org/Tweetsched/tweetsched-cron)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

# tweetsched-cron

Cron jobs service for the Scheduled Tweets service.

## Requirements:
- Java 8 or higher
- Gradle 4.8 or higher

## How to build:
`gradle release -DbuildNumber=<<<desired_number_of_build>>>`

## How to run locally:
- Configure next app properties in "tweetsched.properties" file:
  - CRON_EXPRESSION (optional)
  - REDIS_URL
  - REDIS_PORT
  - REDIS_PASSWORD
  - OAUTH_CONSUMER_KEY
  - OAUTH_CONSUMER_SECRET
  - OAUTH_ACCESS_TOKEN
  - OAUTH_ACCESS_TOKEN_SECRET
- Copy "tweetsched.properties" file to folder with jar file
- Run `java -jar build/libs/tweetsched-cron-1.<<<desired_number_of_build>>>.jar`
