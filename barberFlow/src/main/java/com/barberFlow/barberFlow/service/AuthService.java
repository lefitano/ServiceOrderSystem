package com.barberFlow.barberFlow.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barberFlow.barberFlow.dto.LoginRequest;
import com.barberFlow.barberFlow.dto.LoginResponse;
import com.barberFlow.barberFlow.exception.CredenciaisInvalidasException;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.model.Cliente;
import com.barberFlow.barberFlow.model.Profissional;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.repository.ClienteRepository;
import com.barberFlow.barberFlow.repository.ProfissionalRepository;
import com.barberFlow.barberFlow.repository.UsuarioRepository;
import com.barberFlow.barberFlow.security.AcessoService;
import com.barberFlow.barberFlow.security.JwtService;
import com.barberFlow.barberFlow.security.LoginAttemptService;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;
    private final ProfissionalRepository profissionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;
    private final AcessoService acessoService;

  
    private final String hashFalso;

    public AuthService(UsuarioRepository usuarioRepository, ClienteRepository clienteRepository,
                       ProfissionalRepository profissionalRepository, PasswordEncoder passwordEncoder,
                       JwtService jwtService, LoginAttemptService loginAttemptService, AcessoService acessoService) {
        this.usuarioRepository = usuarioRepository;
        this.clienteRepository = clienteRepository;
        this.profissionalRepository = profissionalRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.loginAttemptService = loginAttemptService;
        this.acessoService = acessoService;
        this.hashFalso = passwordEncoder.encode(UUID.randomUUID().toString());
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        String chave = request.email();
        loginAttemptService.verificarBloqueio(chave);

        Optional<Usuario> encontrado = usuarioRepository.findByEmail(request.email());
        boolean senhaCorreta = encontrado
            .map(u -> passwordEncoder.matches(request.senha(), u.getSenhaHash()))
            .orElseGet(() -> {
                passwordEncoder.matches(request.senha(), hashFalso);
                return false;
            });

        if (!senhaCorreta) {
            loginAttemptService.registrarFalha(chave);
            throw new CredenciaisInvalidasException();
        }

        loginAttemptService.limpar(chave);
        return montarResposta(encontrado.get(), jwtService.gerar(encontrado.get()));
    }

    @Transactional(readOnly = true)
    public LoginResponse usuarioLogado() {
        Integer usuarioId = acessoService.usuarioAtual()
            .orElseThrow(() -> new CredenciaisInvalidasException());
        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com id: " + usuarioId));
        return montarResposta(usuario, null);
    }

    private LoginResponse montarResposta(Usuario usuario, String token) {
        Integer clienteId = clienteRepository.findByUsuarioId(usuario.getId()).map(Cliente::getId).orElse(null);
        Integer profissionalId = profissionalRepository.findByUsuarioId(usuario.getId()).map(Profissional::getId).orElse(null);
        return new LoginResponse(token, token == null ? null : "Bearer",
            token == null ? 0 : jwtService.expiracaoEmSegundos(),
            usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getFuncao(), clienteId, profissionalId);
    }
}
