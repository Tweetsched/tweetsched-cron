[![Build Status](https://travis-ci.org/Tweetsched/tweetsched-cron.svg?branch=master)](https://travis-ci.org/Tweetsched/tweetsched-cron)
[![MIT licensed](https://img.shields.io/badge/license-MIT-blue.svg)](./LICENSE)

# tweetsched-cron

Cron jobs service for the Scheduled Tweets service.

## Requirements:
 - Java 8 or higher
 - Maven 3.3.3 or higher

## How to build:
`mvn clean package`

## How to run locally:
- Configure next environment variables:
  - REDIS_URL
  - REDIS_PORT
  - REDIS_PASSWORD
  - OAUTH_CONSUMER_KEY
  - OAUTH_CONSUMER_SECRET
  - OAUTH_ACCESS_TOKEN
  - OAUTH_ACCESS_TOKEN_SECRET
- Run `java -jar target/tweetsched-cron-0.1-SNAPSHOT.jar`
