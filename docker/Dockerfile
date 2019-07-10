FROM java
ADD JpaTest-0.1.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="$JAVA_OPTS -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -Xms512m -Xmx512m -Xmn512m -Xss256k -XX:SurvivorRatio=8 -XX:+UseConcMarkSweepGC -Duser.timezone=GMT+08"
ENTRYPOINT ["java $JAVA_OPTS","-Djava.security.egd=file:/dev/./urandom","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app.jar"]