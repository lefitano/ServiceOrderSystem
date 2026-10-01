package com.barberflow.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.barberflow.repository.UsuarioRepository;
import com.barberflow.mapper.UsuarioMapper;
import com.barberflow.model.Usuario;
import com.barberflow.dto.UsuarioRequest;
import com.barberflow.dto.UsuarioResponse;
import com.barberflow.exception.RecursoNaoEncontradoException;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public UsuarioResponse criar(UsuarioRequest request){
        Usuario usuario = UsuarioMapper.toEntity(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));


        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuarioSalvo);
    }

    public UsuarioResponse buscarPorId(Integer id){
        Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com id : " + id));
        return UsuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponse> listarTodos(){
       return usuarioRepository.findAll()
        .stream()
        .map(UsuarioMapper::toResponse)
        .toList();
    }

    public UsuarioResponse atualizar(Integer id, UsuarioRequest request){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado de id : " + id));
        
            usuario.setNome(request.nome());
            usuario.setEmail(request.email());
            usuario.setSenhaHash(passwordEncoder.encode(request.senha()));

            Usuario usuarioAtualizado = usuarioRepository.save(usuario);

            return UsuarioMapper.toResponse(usuarioAtualizado);
    }

    public void deletar(Integer id){
       if(!usuarioRepository.existsById(id)){
        throw new RecursoNaoEncontradoException("Usuário não encontrado com id : " + id);
       }

       usuarioRepository.deleteById(id);

    }

}