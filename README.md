# Welcome to AutoRota!



AutoRotarequires [Java 8 ](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html) to run.
The application is currently running on a MySQL localhost sever. Please use the instructions provided by the university to connect to the  [University of Leicester MySQL instructions ](https://campus.cs.le.ac.uk/labsupport/usinglinux/mysqlaccountdetails)

In the  src\main\resources\application.properties
Comment the following code

```sh
spring.jpa.database=mysql  
spring.datasource.url=jdbc:mysql://${MYSQL_HOST:localhost}:3306/sys  
spring.datasource.username=root  
spring.datasource.password=password
```
Uncomment the DbConfig.java code and enter your username and password as explained by the UOL documenation 
```
private String USERNAME = "sfk7"; 
private String PASSWORD = "vaN6aili"; // in ~/.my.cnf vaN6aili
```
In terminal run the following then enter your linux password
```
ssh -fNg -L 3307:mysql.mcscw3.le.ac.uk:3306 xyz123@xanthus.mcscw3.le.ac.uk
```
To run the project on terminal run the following command at go to /code/trunk 

Windows
```
gradlew bootrun
```

Mac
```
./gradlew bootrun
```

If there are any issues with the API calls, please check respective websites for details an check status
Weather: [WeatherBit ](https://www.weatherbit.io/)
Bank Holiday:  [Calendarific](https://www.weatherbit.io/)
