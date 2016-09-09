sleep 10
java -Xms128m -Xmx512m -XX:MaxPermSize=256m  -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar