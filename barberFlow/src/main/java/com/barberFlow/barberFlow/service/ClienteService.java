package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.model.Cliente;
import com.barberFlow.barberFlow.model.Usuario;
import com.barberFlow.barberFlow.repository.ClienteRepository;
import com.barberFlow.barberFlow.repository.UsuarioRepository;
import com.barberFlow.barberFlow.mapper.ClienteMapper;
import com.barberFlow.barberFlow.dto.ClienteResponse;
import com.barberFlow.barberFlow.dto.ClienteRequest;
import com.barberFlow.barberFlow.dto.AtualizarClienteRequest;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import com.barberFlow.barberFlow.exception.OperacaoInvalidaException;



@Service 
@RequiredArgsConstructor 
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ClienteResponse criar(ClienteRequest request){
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
        }

        Usuario usuario = ClienteMapper.toUsuario(request);
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));

        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        Cliente cliente = ClienteMapper.toCliente(request, usuarioSalvo);
        Cliente clienteSalvo = clienteRepository.save(cliente);

        return ClienteMapper.toResponse(clienteSalvo);
    }

    public ClienteResponse buscarPorId(Integer id){
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Não encontrado o cliente de id : " + id));
        return ClienteMapper.toResponse(cliente);
    }

    public List <ClienteResponse> listarTodos(){
        return clienteRepository.findAll()
        .stream()
        .map(ClienteMapper::toResponse)
        .toList();
    }

    @Transactional
    public ClienteResponse atualizar(Integer id, AtualizarClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id: " + id));
    
        Usuario usuario = cliente.getUsuario();
        if (usuarioRepository.existsByEmailAndIdNot(request.email(), usuario.getId())) {
            throw new OperacaoInvalidaException("Já existe um usuário com o email: " + request.email());
        }
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
    
        if (request.senha() != null && !request.senha().isBlank()) {
            usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        }
    
        usuarioRepository.save(usuario);
    
        cliente.setTelefone(request.telefone());
        Cliente clienteAtualizado = clienteRepository.save(cliente);
    
        return ClienteMapper.toResponse(clienteAtualizado);
    }

    @Transactional
    public void deletar(Integer id){
        if(!clienteRepository.existsById(id)){
         throw new RecursoNaoEncontradoException("Cliente não encontrado com id : " + id);
        }
 
        Cliente cliente = clienteRepository.findById(id).get();
        clienteRepository.delete(cliente);
        usuarioRepository.delete(cliente.getUsuario());
 
     }
}