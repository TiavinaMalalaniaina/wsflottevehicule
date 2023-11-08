# Utilisez une image de base avec Java 17
FROM openjdk:17-jre

# Créez un répertoire pour votre application
WORKDIR /app

# Copiez le fichier JAR de votre application dans le conteneur
COPY target/wsflottevehicule-0.0.1-SNAPSHOT.jar /app/app.jar

# Exposez le port sur lequel votre application Spring écoutera
EXPOSE 8080

# Commande pour exécuter votre application Spring
CMD ["java", "-jar", "app.jar"]


FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]