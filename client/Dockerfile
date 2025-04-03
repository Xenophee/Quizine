# Étape 1 : Build de l'application Angular
FROM node:22-alpine AS build

# Définit le répertoire de travail
WORKDIR /app

# Copie les fichiers de dépendances et de configuration
COPY package*.json angular.json tsconfig.json ./

# Installe les dépendances du projet
RUN npm ci

# Copie le reste des fichiers du projet dans le conteneur
COPY . .

# Compile l'application Angular en mode production
RUN npm run build


# Étape 2 : Serveur léger avec Nginx
FROM nginx:alpine

# Ajoute un utilisateur non root
RUN addgroup -S nginxgroup && adduser -S nginxuser -G nginxgroup

# Copie le fichier de configuration Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Définit l'utilisateur non root pour exécuter Nginx
USER nginxuser

# Définit le répertoire de travail pour Nginx
WORKDIR /usr/share/nginx/html

# Copie les fichiers construits depuis l'étape build dans le répertoire Nginx
COPY --from=build /app/dist/* ./

# Expose les ports 80 et 443 pour le trafic HTTP et HTTPS
EXPOSE 80 443

# Démarre Nginx en mode non daemon
CMD ["nginx", "-g", "daemon off;"]
