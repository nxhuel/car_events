package com.nxhu.eventosDeAutos.exception.user;

public class ExceptionUserNotFound extends RuntimeException {
    public ExceptionUserNotFound(Long userId) {
        super(String.format("No existe el usuario con el ID: %d", userId));
    }
}
