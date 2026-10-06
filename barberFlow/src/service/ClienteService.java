package com.barberflow.service;

import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import com.barberflow.model.Cliente;
import com.barberflow.model.Usuario;
import com.barberflow.repository.ClienteRepository;
import com.barberflow.repository.UsuarioRepository;
import com.barberflow.mapper.ClienteMapper;
import com.barberflow.dto.ClienteResponse;
import com.barberflow.dto.ClienteRequest;
import com.barberflow.exception.RecursoNaoEncontradoException;



@Service 
@RequiredArgsConstructor 
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public ClienteResponse criar(ClienteRequest request){
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

    public ClienteResponse atualizar(Integer id, AtualizarClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RecursoNaoEncontradoException("Cliente não encontrado com id: " + id));
    
        Usuario usuario = cliente.getUsuario();
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

    public void deletar(Integer id){
        if(!clienteRepository.existsById(id)){
         throw new RecursoNaoEncontradoException("Cliente não encontrado com id : " + id);
        }
 
        clienteRepository.deleteById(id);
 
     }
}