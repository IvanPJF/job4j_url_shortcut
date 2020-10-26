# job4j_url_shortcut
Replacing site links to this service

[![Build Status](https://travis-ci.org/IvanPJF/job4j_url_shortcut.svg?branch=main)](https://travis-ci.org/IvanPJF/job4j_url_shortcut)

## About the project

### Technologies:
+ Spring Boot Data JPA
+ Spring Boot WEB
+ Spring Boot Security
+ JWT
+ PostgreSQL
+ Jackson

### App interface

+ __Registration__

![Reg](/images/reg.png "Registration")

+ __Authentication and token acquisition__

![Auth](/images/login.png "Authentication")

+ __Converting url to code__

![Convert](/images/convert.png "Converting url")

+ __Getting url by code__

![Redirect](/images/redirect.png "Getting url")

+ __Getting url request statistics__

![Stat](/images/statistic.png "Statistics")

### Deployment

+ Copy the project from github
+ Go to the root directory of the project
+ Execute the command `mvn clean package -DskipTests=true`
+ Go to the `target` directory
+ Execute the command `java -jar url_shortcut-0.1.jar`
+ The app works and you can use it

### Contact

Telegram: https://t.me/IvanPJF