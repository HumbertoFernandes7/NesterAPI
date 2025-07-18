# Estágio de construção
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src ./src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Estágio de execução
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/nester-0.0.1-SNAPSHOT.jar nester.jar
# Cria o diretório de imagens dentro de /app
RUN mkdir -p /app/data/imagens/
# Copia a foto padrão para o novo diretório persistente
COPY src/main/resources/static/images/padrao.jpg /app/data/imagens/padrao.jpg
COPY src/main/resources/static/images/humbertofernandes08@hotmail.comfoto-perfil.jpg /app/data/imagens/humbertofernandes08@hotmail.comfoto-perfil.jpg
# Garante permissões de leitura/escrita para o diretório
RUN chmod -R 777 /app/data/imagens/ # Permissões abertas para garantir, você pode restringir depois
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/nester.jar"]