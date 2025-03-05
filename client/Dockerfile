# Étape 1 : Build de l'application Angular
FROM node:22-alpine AS build

# Définit le répertoire de travail
WORKDIR /app

# Copie les fichiers package.json et package-lock.json pour installer les dépendances
COPY package*.json ./

# Vérifie les vulnérabilités critiques
RUN npm audit --audit-level=critical || true

# Installe les dépendances du projet
RUN npm install

# Copie le reste des fichiers du projet dans le conteneur
COPY . .

# Compile l'application Angular en mode production
RUN npm run build --prod


# Étape 2 : Serveur léger avec Nginx
FROM nginx:alpine

# Ajoute un utilisateur non root
RUN addgroup -S nginxgroup && adduser -S nginxuser -G nginxgroup \
    && chown -R nginxuser:nginxgroup /usr/share/nginx/html /var/cache/nginx /var/run /etc/nginx

USER nginxuser

# Copie les fichiers construits depuis l'étape build dans le répertoire Nginx
COPY --from=build /app/dist/ /usr/share/nginx/html/

# Expose le port 80 (port par défaut pour Nginx)
EXPOSE 80

# Démarre Nginx en mode non daemon
CMD ["nginx", "-g", "daemon off;"]
