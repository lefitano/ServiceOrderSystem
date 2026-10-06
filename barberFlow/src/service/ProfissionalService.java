package com.barberflow.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.barberflow.model.Profissional;
import com.barberflow.model.Usuario;
import com.barberflow.repository.ProfissionalRepository;
import com.barberflow.repository.UsuarioRepository;
import com.barberflow.mapper.ProfissionalMapper;
import com.barberflow.dto.ProfissionalResponse;
import com.barberflow.dto.ProfissionalRequest;
import com.barberflow.dto.AtualizarProfissionalRequest;
import com.barberflow.exception.RecursoNaoEncontradoException;

@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfissionalRepository profissionalRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfissionalResponse criar(ProfissionalRequest request){
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

    public ProfissionalResponse atualizar(Integer id, AtualizarProfissionalRequest request){
        Profissional profissional = profissionalRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id));

        Usuario usuario = profissional.getUsuario();
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

    public void deletar(Integer id){
        if (!profissionalRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Profissional não encontrado com id: " + id);
        }

        profissionalRepository.deleteById(id);
    }
}