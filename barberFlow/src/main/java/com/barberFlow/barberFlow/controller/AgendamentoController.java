package com.barberFlow.barberFlow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.barberFlow.barberFlow.dto.AgendamentoRequest;
import com.barberFlow.barberFlow.dto.AgendamentoResponse;
import com.barberFlow.barberFlow.model.StatusAgendamento;
import com.barberFlow.barberFlow.service.AgendamentoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @PreAuthorize("hasRole('ADMIN') or (hasRole('CLIENTE') and @acesso.ehCliente(#request.clienteId()))")
    @PostMapping
    public ResponseEntity<AgendamentoResponse> criar(@Valid @RequestBody AgendamentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoService.criar(request));
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.podeVerAgendamento(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<AgendamentoResponse>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.ehCliente(#clienteId)")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorCliente(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(agendamentoService.listarPorCliente(clienteId));
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.ehProfissional(#profissionalId)")
    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<AgendamentoResponse>> listarPorProfissional(@PathVariable Integer profissionalId) {
        return ResponseEntity.ok(agendamentoService.listarPorProfissional(profissionalId));
    }

    @PreAuthorize("hasRole('ADMIN') or @acesso.podeAlterarStatus(#id, #status)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<AgendamentoResponse> atualizarStatus(@PathVariable Integer id, @RequestParam StatusAgendamento status) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, status));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
