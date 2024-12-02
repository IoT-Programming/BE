# 1단계: 빌드 단계
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /IoTProject

# 소스 코드 복사
COPY . .

# Gradle 프로젝트 빌드
RUN gradle clean build -x test

# 2단계: 실행 단계
FROM openjdk:17-jdk-alpine
WORKDIR /IoTProject

# 빌드 단계에서 생성된 JAR 파일 복사
COPY --from=build /IoTProject/build/libs/*.jar app.jar

# 포트 설정 (예: 8080)
EXPOSE 8080

# 애플리케이션 실행 명령어
ENTRYPOINT ["java","-jar","/app.jar"]