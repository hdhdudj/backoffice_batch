package io.spring.main.dao.user;

public interface EncryptService {
    String encrypt(String password);
    boolean check(String checkPassword, String realPassword);
}
