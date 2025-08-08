# LogAnalyzer

A Java application for analyzing log files and generating reports.

## Requirements

- Java 21
- Maven 3.6\+

## Build

```sh
mvn clean install
```
## Run 
```sh
java -jar target/Taboola-1.0-SNAPSHOT.jar
```
## Test
```sh
mvn test
```

### Features

 - Log parsing and analysis
 - Report generation by OS, browser, etc.
 - Extensible collector and reporter architecture.


### Development

- Uses Lombok for boilerplate reduction
- Mockito and JUnit 5 for testing

### Notes
<p>
Ensure JAVA_HOME points to a Java 21 installation.
For mocking support on Java 21, latest Mockito and Byte Buddy are required (already configured in pom.xml).
</p>