# Sample web application ![Travis CI status](https://travis-ci.com/AnatolyevPavel/demowebapp.svg?branch=master)
Sample Spring Boot web application to test a [servlet filter](https://github.com/AnatolyevPavel/webmetrics) that gathers the following metrics:
* Request Time: time spent between when the application starts to process the request and the time when the application sends the response to the client.
* Response Sizes: the size of the HTTP response body in bytes.
* Metrics: minimum, average, and maximum for Request Time and Response Size.

Web application consists of five pages:
1. Index page with general info and links to another pages.
2. Metrics summary page, that shows summary infromation: minimum, average, and maximum for Request Time and Response Size.
3. Page that allows searching for stored metrics using metrics ID added to the response by the filter.
4. Page that shows all metrics, collected by the filter.
5. Page that shows random number (between 1 to 100) of "Lorem ipsum" text with random (0-10 seconds) delay.

## Dependencies
The application uses servlet filter Maven package https://github.com/AnatolyevPavel/webmetrics/packages/417488. To build the application one must add Github personal token with read:packages permission to `~/.m2/settings.xml` file to server `gihub-packages` because Github does not allow anonymous package download during Maven builds:
```
    <server>
      <id>github-packages</id>
      <username>YOUR_USERNAME</username>
      <password>YOUR_GITHUB_PERSONAL_TOKEN</password>
    </server>
```

## Building application
Run `mvn clean package` to build and package the web application.

## Running application
From `target` folder run `java -jar demowebapp-0.0.1-SNAPSHOT.jar`.
