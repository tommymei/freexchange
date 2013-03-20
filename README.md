# This project was bootstrapped from the following github repo

https://github.com/taylorleese/google-app-engine-jappstart/blob/master/README.md

Summary of features imported 

## Features
* Appstats Support
* Google AJAX Library API (jQuery)
* JRebel Support
* Local Development Console Support (http://localhost:8080/_ah/admin)
* Maven Support
  * CSS/JS minification via the yuicompressor-maven-plugin
  * Uses the maven-gae-plugin
* Remote API/Bulk Loader Support
* Sitemesh Integration
* Spring 3
  * JPA support
  * JSON/AJAX integration (Jackson)
  * JSR-303 validation
  * Localization support
* Spring Security 3
  * Authentication
  * Expression based access control
  * Fully integrated with the App Engine Datastore and Memcache
  * Login/create account functionality with e-mail confirmation
  * Remember Me
  * Support for hierarchical roles
* Static Error Handler Support
* Task Queue Support
* URL Rewrite Integration


## Features added by me

* Appengine's Blobstore service to store media files 
* JDO 2.3 annotations to work with Appengine's schemaless object datastore

TODOS
- Used Bootstrap to skeleton UI screens (http://twitter.github.com/bootstrap/)



## Build/Deploy
* Run `mvn clean package -P local` to create a new local build
* Run `mvn gae:run -P local` to run locally
* Run `mvn gae:stop -P local` to stop the local jetty server
* Run `mvn clean package -P dev` to create a new dev build
* Run `mvn gae:deploy -P dev` to deploy to your dev app engine app
* Run `mvn clean package -P prod` to create a new prod build
* Run `mvn gae:deploy -P prod` to deploy to your prod app engine app