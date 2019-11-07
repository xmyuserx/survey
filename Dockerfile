FROM maven:3.6.0-jdk-8-alpine AS BUILD

WORKDIR /build

# Кэшируем зависимости
COPY pom.xml ./
RUN mvn -DskipTests verify clean --fail-never

COPY . ./
RUN mvn -DskipTests install

FROM openjdk:8-jre-stretch AS RELEASE

COPY --from=BUILD /build/target/survey-*.jar ./survey.jar

CMD ["java", "-jar", "./survey.jar"]