#!/bin/bash
#config server
export SERVER_ADDRESS = "localhost"
export SERVER_PORT = "8080"
export SERVER_NAME = "XEEX Chat Server"
#config database
export DB_HOST_URL = "jdbc:mysql://localhost:3306/xeex?useSSL=false&serverTimezone=UTC"
export DB_USERNAME = "root"
export DB_PASSWORD = ""
export DB_DRIVER = "com.mysql.cj.jdbc.Driver"
export DB_DIALECT = "org.hibernate.dialect.MySQL5Dialect"
#config token
export JWT_SECRET = "FroDQsG+vJBwjbSsnIIOGVALBnxgulzqp6NzjH6aGg50PKDUze+XLrOl3uMJmd13"
#24 hours token expiration time
export JWT_EXPIRATION_TIME = "86400"