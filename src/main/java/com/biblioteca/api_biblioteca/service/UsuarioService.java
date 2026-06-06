package com.biblioteca.api_biblioteca.service;

import com.biblioteca.api_biblioteca.dto.UsuarioRequestDTO;
import com.biblioteca.api_biblioteca.dto.UsuarioResponseDTO;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.UsuarioRepository;
import com.biblioteca.api_biblioteca.exception.RecursoNaoEncontradoException;
import com.biblioteca.api_biblioteca.exception.ConflitoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    private UsuarioResponseDTO toDTO(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getTelefone()
        );
    }

    private Usuario toEntity(UsuarioRequestDTO dto){
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setCpf(dto.getCpf());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        return usuario;
    }

    public List<UsuarioResponseDTO> listarTodos(){
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public Usuario buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com id: " + id));
    }

    public UsuarioResponseDTO buscarPorIdDTO(Long id){
        return toDTO(buscarPorId(id));
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto){
        if (repository.existsByCpf(dto.getCpf())) {
            throw new ConflitoException("CPF já cadastrado");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new ConflitoException("E-mail já cadastrado");
        }
        return toDTO(repository.save(toEntity(dto)));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto){
        Usuario usuario = buscarPorId(id);
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        return toDTO(repository.save(usuario));
    }

    public void deletar(Long id){
        buscarPorId(id);
        repository.deleteById(id);
    }

}
