package com.barberFlow.barberFlow.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.barberFlow.barberFlow.repository.UsuarioRepository;
import com.barberFlow.barberFlow.mapper.UsuarioMapper;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.dto.UsuarioRequest;
import com.barberFlow.barberFlow.dto.UsuarioResponse;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public UsuarioResponse criar(UsuarioRequest request){
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
        }

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

    @Transactional
    public UsuarioResponse atualizar(Integer id, UsuarioRequest request){
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado de id : " + id));
        
            if (usuarioRepository.existsByEmailAndIdNot(request.email(), id)) {
                throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
            }
            usuario.setNome(request.nome());
            usuario.setEmail(request.email());
            usuario.setSenhaHash(passwordEncoder.encode(request.senha()));

            Usuario usuarioAtualizado = usuarioRepository.save(usuario);

            return UsuarioMapper.toResponse(usuarioAtualizado);
    }

    @Transactional
    public void deletar(Integer id){
       if(!usuarioRepository.existsById(id)){
        throw new RecursoNaoEncontradoException("Usuário não encontrado com id : " + id);
       }

       usuarioRepository.deleteById(id);

    }

}