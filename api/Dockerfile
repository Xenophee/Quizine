# Étape 1: Build avec JDK et Gradle
FROM eclipse-temurin:21-jdk-alpine AS builder

# Définit le répertoire de travail
WORKDIR /app

# Copie uniquement les fichiers liés à la configuration Gradle (pour utiliser le cache Docker)
COPY gradle/ gradle/
COPY settings.gradle.kts build.gradle.kts gradlew ./

# Vérifie les dépendances pour détecter d'éventuelles failles de sécurité
RUN ./gradlew dependencyCheckAnalyze --no-daemon || true

# Télécharge les dépendances sans compiler le code source
RUN ./gradlew dependencies --no-daemon # ajouter || true si on veut passer les erreurs

# Copie le reste du code source
COPY src/ src/

# Compile l’application sans exécuter les tests
RUN ./gradlew build -x test --no-daemon


# Étape 2: Exécution avec une image plus légère (JRE uniquement)
FROM eclipse-temurin:21-jre-alpine

# Ajoute un utilisateur non-root pour exécuter l'application
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Définit le répertoire de travail
WORKDIR /app

# Copie uniquement le fichier JAR généré
COPY --from=builder /app/build/libs/*.jar app.jar

# Lance l’application avec optimisation mémoire
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]
