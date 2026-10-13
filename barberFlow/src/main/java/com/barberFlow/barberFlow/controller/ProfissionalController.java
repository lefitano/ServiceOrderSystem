package com.barberFlow.barberFlow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.barberFlow.barberFlow.dto.AtualizarProfissionalRequest;
import com.barberFlow.barberFlow.dto.ProfissionalRequest;
import com.barberFlow.barberFlow.dto.ProfissionalResponse;
import com.barberFlow.barberFlow.service.ProfissionalService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profissionais")
@RequiredArgsConstructor
public class ProfissionalController {

    private final ProfissionalService profissionalService;

    @PostMapping
    public ResponseEntity<ProfissionalResponse> criar(@Valid @RequestBody ProfissionalRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(profissionalService.criar(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(profissionalService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponse>> listarTodos() {
        return ResponseEntity.ok(profissionalService.listarTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> atualizar(@PathVariable Integer id, @Valid @RequestBody AtualizarProfissionalRequest request) {
        return ResponseEntity.ok(profissionalService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
