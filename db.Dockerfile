FROM mysql:8
MAINTAINER mpriyank.dev@gmail.com

ENV MYSQL_DATABASE=url-shortener-db \
    MYSQL_ROOT_PASSWORD=rootpassword

ADD schema.sql /docker-entrypoint-initdb.d

EXPOSE 3306