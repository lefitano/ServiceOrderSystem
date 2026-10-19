package com.barberFlow.barberFlow.security;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.barberFlow.barberFlow.model.Agendamento;
import com.barberFlow.barberFlow.model.StatusAgendamento;
import com.barberFlow.barberFlow.repository.AgendamentoRepository;
import com.barberFlow.barberFlow.repository.ClienteRepository;
import com.barberFlow.barberFlow.repository.ProfissionalRepository;

import lombok.RequiredArgsConstructor;


@Component("acesso")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcessoService {

    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final AgendamentoRepository agendamentoRepository;

    public Optional<Integer> usuarioAtual() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken token) {
            try {
                return Optional.of(Integer.valueOf(token.getToken().getSubject()));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean ehCliente(Integer clienteId) {
        return usuarioAtual()
            .flatMap(clienteRepository::findByUsuarioId)
            .map(cliente -> cliente.getId().equals(clienteId))
            .orElse(false);
    }

    public boolean ehProfissional(Integer profissionalId) {
        return usuarioAtual()
            .flatMap(profissionalRepository::findByUsuarioId)
            .map(profissional -> profissional.getId().equals(profissionalId))
            .orElse(false);
    }

    public boolean podeVerAgendamento(Integer agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
            .map(a -> ehClienteDoAgendamento(a) || ehProfissionalDoAgendamento(a))
            .orElse(true);
    }

    public boolean ehDonoDoAgendamento(Integer agendamentoId) {
        return agendamentoRepository.findById(agendamentoId)
            .map(this::ehClienteDoAgendamento)
            .orElse(true);
    }

    public boolean podeAlterarStatus(Integer agendamentoId, StatusAgendamento novoStatus) {
        return agendamentoRepository.findById(agendamentoId)
            .map(a -> ehProfissionalDoAgendamento(a)
                || (ehClienteDoAgendamento(a) && novoStatus == StatusAgendamento.CANCELADO))
            .orElse(true);
    }

    private boolean ehClienteDoAgendamento(Agendamento agendamento) {
        return usuarioAtual().map(id -> id.equals(agendamento.getCliente().getUsuario().getId())).orElse(false);
    }

    private boolean ehProfissionalDoAgendamento(Agendamento agendamento) {
        return usuarioAtual().map(id -> id.equals(agendamento.getProfissional().getUsuario().getId())).orElse(false);
    }
}
