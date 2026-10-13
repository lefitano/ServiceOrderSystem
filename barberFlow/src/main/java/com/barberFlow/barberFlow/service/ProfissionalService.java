package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.repository.ProfissionalRepository;
import com.barberFlow.barberFlow.repository.UsuarioRepository;
import com.barberFlow.barberFlow.mapper.ProfissionalMapper;
import com.barberFlow.barberFlow.dto.ProfissionalResponse;
import com.barberFlow.barberFlow.dto.ProfissionalRequest;
import com.barberFlow.barberFlow.dto.AtualizarProfissionalRequest;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ProfissionalResponse criar(ProfissionalRequest request){
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
        }

        Usuario usuario = ProfissionalMapper.toUsuario(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Profissional profissional = ProfissionalMapper.toProfissional(request, usuarioSalvo);
        Profissional profissionalSalvo = profissionalRepository.save(profissional);

        return ProfissionalMapper.toResponse(profissionalSalvo);
    }

    public ProfissionalResponse buscarPorId(Integer id){
        Profissional profissional = profissionalRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id));
        return ProfissionalMapper.toResponse(profissional);
    }

    public List<ProfissionalResponse> listarTodos(){
        return profissionalRepository.findAll()
            .stream()
            .map(ProfissionalMapper::toResponse)
            .toList();
    }

    @Transactional
    public ProfissionalResponse atualizar(Integer id, AtualizarProfissionalRequest request){
        Profissional profissional = profissionalRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id));

        Usuario usuario = profissional.getUsuario();
        if (usuarioRepository.existsByEmailAndIdNot(request.email(), usuario.getId())) {
            throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
        }
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        if (request.senha() != null && !request.senha().isBlank()) {
            usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        }

        usuarioRepository.save(usuario);

        profissional.setEspecialidade(request.especialidade());
        profissional.setBio(request.bio());
        Profissional profissionalAtualizado = profissionalRepository.save(profissional);

        return ProfissionalMapper.toResponse(profissionalAtualizado);
    }

    @Transactional
    public void deletar(Integer id){
        if (!profissionalRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id);
        }

        Profissional profissional = profissionalRepository.findById(id).get();
        profissionalRepository.delete(profissional);
        usuarioRepository.delete(profissional.getUsuario());
    }
}