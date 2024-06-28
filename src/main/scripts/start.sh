
#***********************************************************************************#
#		this bash script use for running chat application's 
#					on background process model 
#		with only one command from remote client (via SSH, PUTTY)						
#						  by: v_long - BRC VN
#***********************************************************************************#
sh
#!/bin/bash

# Nạp các biến môi trường từ setenv.sh nếu có
source ./setenv.sh

#
#kill all existed processes
#ps -elf | awk '/xeex_chat.jar/ && !/awk/ {print $4}' | xargs -r kill -s SIGTERM

#start run all jars on background

#nohup java -jar xeex_chat.jar
spring.config.location=./resources/application.properties > /dev/null 2>&1 &

#listing all proccesses
#ps -aux | grep java
# Chạy ứng dụng Spring Boot với cấu hình từ thư mục config
java -jar xeex_chat.jar --spring.config.location=./resources/
echo "XEEX Chat Server are running..."

