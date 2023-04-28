FROM maven:3.9.1-amazoncorretto-19@sha256:f03397d19ff99a9348bcd29b42eacd96cba06bc88ccaf6acf1603706e8dd80e1 AS BUILDER
WORKDIR /karatestudents
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /karatestudents/src
RUN mvn package -DskipTests
FROM amazoncorretto:19-alpine@sha256:21cc27d3f3a4a79b32c060bd55576a22922a2a70bfe7a6b3a886ad8119ecc174
WORKDIR /app
RUN addgroup --system javauser
RUN adduser -S -s /bin/false -G javauser javauser
COPY --from=BUILDER /karatestudents/target/karatestudents-0.0.1-SNAPSHOT.jar /app/application.jar
RUN chown -R javauser:javauser /app
EXPOSE 8080
USER javauser
CMD ["java", "-jar", "application.jar"]