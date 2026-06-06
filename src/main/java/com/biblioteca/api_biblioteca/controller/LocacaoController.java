package com.biblioteca.api_biblioteca.controller;

import com.biblioteca.api_biblioteca.dto.LocacaoRequestDTO;
import com.biblioteca.api_biblioteca.dto.LocacaoResponseDTO;
import com.biblioteca.api_biblioteca.service.LocacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locacoes")
public class LocacaoController {

    @Autowired
    private LocacaoService service;

    @GetMapping
    public ResponseEntity<List<LocacaoResponseDTO>> listarTodos(){
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/em-aberto")
    public ResponseEntity<List<LocacaoResponseDTO>> listarEmAberto(){
        return ResponseEntity.ok(service.listarEmAberto());
    }

    @GetMapping("/em-atraso")
    public ResponseEntity<List<LocacaoResponseDTO>> listarEmAtraso(){
        return ResponseEntity.ok(service.listarEmAtraso());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<LocacaoResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(service.listarPorUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<LocacaoResponseDTO> locar(@Valid @RequestBody LocacaoRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.locar(dto));
    }

    @PatchMapping("/{id}/devolver")
    public ResponseEntity<LocacaoResponseDTO> devolver(@PathVariable Long id){
        return ResponseEntity.ok(service.devolver(id));
    }

}