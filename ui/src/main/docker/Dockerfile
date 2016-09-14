#FROM java:8
FROM anapsix/alpine-java
VOLUME /tmp
RUN mkdir /app
ADD ui-1.0.0-SNAPSHOT.jar /app/app.jar
ADD runboot.sh /app/
RUN bash -c 'touch /app/app.jar'
WORKDIR /app
RUN chmod a+x runboot.sh
EXPOSE 801
CMD /app/runboot.sh