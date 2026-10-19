package com.barberFlow.barberFlow.security;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Component;

import com.barberFlow.barberFlow.exception.TentativasExcedidasException;


@Component
public class LoginAttemptService {

    private static final int MAX_FALHAS = 5;
    private static final Duration JANELA = Duration.ofMinutes(15);
    private static final int LIMITE_MAPA = 10_000;

    private record Estado(int falhas, Instant desde) {
        boolean expirou() {
            return Instant.now().isAfter(desde.plus(JANELA));
        }
    }

    private final ConcurrentMap<String, Estado> tentativas = new ConcurrentHashMap<>();

    public void verificarBloqueio(String chave) {
        Estado estado = tentativas.get(chave);
        if (estado == null) {
            return;
        }
        if (estado.expirou()) {
            tentativas.remove(chave);
            return;
        }
        if (estado.falhas() >= MAX_FALHAS) {
            throw new TentativasExcedidasException();
        }
    }

    public void registrarFalha(String chave) {
        if (tentativas.size() > LIMITE_MAPA) {
            tentativas.values().removeIf(Estado::expirou);
        }
        tentativas.compute(chave, (k, atual) ->
            atual == null || atual.expirou() ? new Estado(1, Instant.now()) : new Estado(atual.falhas() + 1, atual.desde()));
    }

    public void limpar(String chave) {
        tentativas.remove(chave);
    }
}
