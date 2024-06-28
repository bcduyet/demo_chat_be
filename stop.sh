#***********************************************************************************#
#		this bash script use for STOP chat application's #				
#		with only one command from remote client (via SSH, PUTTY)						
#						  by: v_long - BRC VN
#***********************************************************************************#
#!/bin/bash

#kill all existed processes
ps -elf | awk 'xeex_chat.jar/ && !/awk/ {print $4}' | xargs -r kill -s SIGTERM
#listing all proccesses
ps -aux | grep java
echo "XEEX Chatting application stopped!"