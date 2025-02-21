# Étape 1: Build avec JDK et Gradle
FROM eclipse-temurin:21-jdk-alpine AS builder

# Définit le répertoire de travail
WORKDIR /app

# Copie uniquement les fichiers liés à la configuration Gradle (pour utiliser le cache Docker)
COPY gradle/ gradle/
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradlew .

# Télécharge les dépendances sans compiler le code source
RUN ./gradlew dependencies --no-daemon # ajouter || true si on veut passer les erreurs

# Copie le reste du code source
COPY . .

# Compile l’application
RUN ./gradlew build --no-daemon

# Étape 2: Exécution avec une image plus légère (JRE uniquement)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copie uniquement le fichier JAR généré
COPY --from=builder /app/build/libs/*.jar app.jar

# Lance l’application avec optimisation mémoire
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
