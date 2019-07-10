FROM java
ADD JpaTest-0.1.jar app.jar
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","/app.jar","--spring.profiles.active=${SPRING_PROFILES_ACTIVE}","-c"]