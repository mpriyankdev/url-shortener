From openjdk:8
MAINTAINER mpriyank.dev@gmail.com
ADD target/url-shortener-0.0.1-SNAPSHOT.jar url-shortener.jar
EXPOSE 9090
CMD ["java","-jar","url-shortener.jar"]