package com.barberFlow.barberFlow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoRequest;
import com.barberFlow.barberFlow.dto.HorarioDeTrabalhoResponse;
import com.barberFlow.barberFlow.service.HorarioDeTrabalhoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/horarios-trabalho")
@RequiredArgsConstructor
public class HorarioDeTrabalhoController {

    private final HorarioDeTrabalhoService horarioDeTrabalhoService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<HorarioDeTrabalhoResponse> criar(@Valid @RequestBody HorarioDeTrabalhoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(horarioDeTrabalhoService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioDeTrabalhoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(horarioDeTrabalhoService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<HorarioDeTrabalhoResponse>> listarTodos() {
        return ResponseEntity.ok(horarioDeTrabalhoService.listarTodos());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<HorarioDeTrabalhoResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody HorarioDeTrabalhoRequest request) {
        return ResponseEntity.ok(horarioDeTrabalhoService.atualizar(id, request));
    }

    @GetMapping("/profissional/{profissionalId}")
    public ResponseEntity<List<HorarioDeTrabalhoResponse>> listarPorProfissional(@PathVariable Integer profissionalId) {
        return ResponseEntity.ok(horarioDeTrabalhoService.listarPorProfissional(profissionalId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        horarioDeTrabalhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
