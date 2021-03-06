FROM openjdk:latest

ADD build/libs/app-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT ["java", \
    "-Djava.security.egd=file:/dev/./urandom", \
    "-jar", \
    "/app.jar" \
    ]
    
EXPOSE 8080