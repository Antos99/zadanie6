./mvnw clean package \
&& docker build -t zadanie_6 . \
&& docker compose down \
&& docker compose build --no-cache \
&& docker compose up -d --force-recreate\
&& docker logs --follow app

