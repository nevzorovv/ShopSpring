package ru.vnevzorov.Shop.exception;

import java.io.IOException;

public class UserAlreadyExistException extends IOException {
    private String message;
    private boolean emailAlreadyExists;
    private boolean loginAlreadyExists;
    private boolean passwordsDontMatch;

    public UserAlreadyExistException() {
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEmailAlreadyExists() {
        return emailAlreadyExists;
    }

    public void setEmailAlreadyExists(boolean emailAlreadyExists) {
        this.emailAlreadyExists = emailAlreadyExists;
    }

    public boolean isLoginAlreadyExists() {
        return loginAlreadyExists;
    }

    public void setLoginAlreadyExists(boolean loginAlreadyExists) {
        this.loginAlreadyExists = loginAlreadyExists;
    }

    public boolean isPasswordsDontMatch() {
        return passwordsDontMatch;
    }

    public void setPasswordsDontMatch(boolean passwordsDontMatch) {
        this.passwordsDontMatch = passwordsDontMatch;
    }
}
