# CheckRunner
***Application of creation check in shop market by product ids.***
_______

###### ***Used technology:***
<sub>-java 17  
-gradle 7.5  
-Spring Boot 3.0.0  
-Spring Data JPA  
-Spring Web  
-PostgreSQL database  
-Lombok</sub>
-------
#### build project and run jar file from .build/libs folder:
    java - jar checkRunner-spring-boot.jar 1-3 2-1 3-5 4-2 card-1234
#### Result file in root directory with name "check.txt"
![image](https://user-images.githubusercontent.com/86801437/208312948-33de6dd1-04b9-4338-be99-c63f1bddcfba.png)
_______
#### Get product list:
    [GET] http://localhost:8080/products

#### Get check by request params:
    [GET] http://localhost:8080/check?id={id}&amount={amount}&card={card number}
-------
![image](https://user-images.githubusercontent.com/86801437/208313537-2eb0b5e3-1720-4b12-b0ae-90c9bed978aa.png)

#### Docker image:
    docker pull 200605030904/checkrunner