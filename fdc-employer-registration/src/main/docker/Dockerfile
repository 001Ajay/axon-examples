FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD fdc-employer-registration-0.0.1-SNAPSHOT.jar app.jar
ADD wait-for-it.sh wait-for-it.sh
RUN sh -c 'chmod +x /wait-for-it.sh'
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT ["sh", "-c", "/wait-for-it.sh"]