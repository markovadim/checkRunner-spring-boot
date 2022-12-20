FROM openjdk:17
COPY ./build/libs/checkRunner-spring-boot.jar checkrunner.jar
ENTRYPOINT ["java","-jar","checkrunner.jar"]