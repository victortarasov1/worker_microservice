# worker

This microservice makes it possible for you to execute scenarios using the Selenium WebDriver. 
Every two minutes, it fetches lists of scenarios and proxies from a remote API and executes them.
[You can see a microservice that provides a queue for scenarios and proxies here.](https://github.com/MorgothGorthaur/publisher_microservice/)
Each scenario must contain a name, a site's URL, and a list of steps. 
Currently, there are three types of steps: 'sleep', 'clickCss', and 'clickXpath'. 
The program also provides the ability for multithreaded execution of scenarios 
(you need to set the number of threads in the property file). 
Additionally, the program logs scenario executions, 
including any exceptions that occur during execution, and saves the logs to a database. 
You can also change the storage type (e.g., file) or output format of logs by configuring logback.xml 
using different appenders.

## Json Examples

### Proxy Example
```json
{
    "proxyNetworkConfig": {
        "hostname": "20.210.113.32",
        "port": 8080
    },
    "proxyCredentials": {
        "username": "",
        "password": ""
    }
}
```
### Scenario Example

```json
{
    "name":"linux scenario",
    "site":"https://kernel.org/",
    "steps" :[
        {
            "action": "sleep",
            "value": "3000:5000"
        },
        {
            "action":"clickCss",
            "value":"#banner > nav > ul > li:nth-child(1) > a"
        },
        {
            "action": "sleep",
            "value": "3000:5000"
        },
        {
            "action":"clickXpath",
            "value": "/html/body/footer/address[2]/a[4]"
        },
        {
            "action": "sleep",
            "value": "3000:5000"
        }
    ]
}
```

## Used Technologies

### Back-end:
- okhttp
- Selenium
- H2
- Logback
- Spring Boot
- AspectJ
- FlyWay
- Mockito
- AssertJ

### Server build:
- maven

## Requirements
- java 17
- maven
- Selenium ChromeDriver
- Google Chrome
- H2 client (optionally)

  Note that the chromedriver version must be similar to your Google Chrome version.


## Run
```bash
    cd .../project/directory/executor-service
    mvn clean flyway:migrate -Dflyway.configFiles=flywayConfiguration.conf
    mvn clean package
    java -jar target/executor-service-1.0-SNAPSHOT.jar 
```

In addition, you need to set the path to your chromedriver in the 'application.properties' file.
If you intend to change the path to the database, as well as the username and password, 
you should configure these settings in both the 'logback.xml' and 'flywayConfiguration.conf' files.

