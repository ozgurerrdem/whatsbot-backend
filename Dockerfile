# Java 21 tabanlı resmi image
FROM eclipse-temurin:21-jdk

# Uygulama jar'ını kopyalamak için önce build yapacağız
WORKDIR /app

# Gradle wrapper ve proje dosyalarını kopyala
COPY . .

# Gradle ile build (testleri atlamak için --no-daemon -x test)
RUN ./gradlew build --no-daemon -x test

# Build sonrası oluşan jar dosyasını çalıştır
# Not: build/libs altında oluşan .jar adını projenin çıktısına göre uyarlayabilirsin
CMD ["java", "-jar", "build/libs/whatsbot-0.0.1-SNAPSHOT.jar"]
