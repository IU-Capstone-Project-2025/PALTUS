FROM node:lts-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

# RUN npm run build

# RUN npm install -g http-server

# EXPOSE 8080

# CMD ["http-server", "dist", "-p", "8080"]

EXPOSE 5173

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]