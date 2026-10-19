package com.barberFlow.barberFlow.exception;

public class TentativasExcedidasException extends RuntimeException {
    public TentativasExcedidasException() {
        super("Muitas tentativas de login falhas. Tente novamente em alguns minutos");
    }
}
