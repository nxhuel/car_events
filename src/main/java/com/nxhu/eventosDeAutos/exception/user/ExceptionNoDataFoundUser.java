package com.nxhu.eventosDeAutos.exception.user;

public class ExceptionNoDataFoundUser extends RuntimeException{
    public ExceptionNoDataFoundUser() {
        super(String.format("No se encontraron usuarios"));
    }
}
