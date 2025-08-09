# Usar una imagen oficial de Maven para compilar la aplicación
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar archivos de configuración
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Segunda fase: ejecutar la aplicación con JDK
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el .jar compilado desde la fase anterior
COPY --from=builder /app/target/restocompras-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto en el que corre la app
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]