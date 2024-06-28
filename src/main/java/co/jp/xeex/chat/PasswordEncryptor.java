package co.jp.xeex.chat;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import lombok.extern.log4j.Log4j;

/**
 * tool to generate encrypted for some environments variables.
 * (only use for testing and development)
 * 
 * @author v_long
 */
@Log4j
public class PasswordEncryptor {
    private static final String MASTER_PWD = "xeex_chat_be_362004";

    public static void main(String[] args) {
        //
        String dbUrl = "jdbc:mysql://192.168.249.122:33061/xeex?useSSL=false&serverTimezone=UTC";
        String dbUsername = "root";
        String dbPwd = "root";
        String jwwtSec = "FroDQsG+vJBwjbSsnIIOGVALBnxgulzqp6NzjH6aGg50PKDUze+XLrOl3uMJmd13";
        //
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(MASTER_PWD);
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        //
        log.info("DB_URL=" + encryptor.encrypt(dbUrl));
        log.info("DB_USERNAME=" + encryptor.encrypt(dbUsername));
        log.info("DB_PASSWORD=" + encryptor.encrypt(dbPwd));
        log.info("JWT_SECRET=" + encryptor.encrypt(jwwtSec));
    }
}
