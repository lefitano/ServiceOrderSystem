package com.barberFlow.barberFlow.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import com.barberFlow.barberFlow.repository.ServicoRepository;
import com.barberFlow.barberFlow.model.Servico;
import com.barberFlow.barberFlow.mapper.ServicoMapper;
import com.barberFlow.barberFlow.dto.ServicoRequest;
import com.barberFlow.barberFlow.dto.ServicoResponse;
import com.barberFlow.barberFlow.exception.RecursoNaoEncontradoException;
import java.util.List;


@Service
@RequiredArgsConstructor 
public class ServicoService {
    private final ServicoRepository servicoRepository;


    @Transactional
    public ServicoResponse criar(ServicoRequest request){
        Servico servico = ServicoMapper.toEntity(request);
        Servico servicoSalvo = servicoRepository.save(servico);

        return ServicoMapper.toResponse(servicoSalvo);
    }

    public ServicoResponse buscarPorId(Integer id){
        Servico servico = servicoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com id: " + id));
        return ServicoMapper.toResponse(servico);
    }

    public List<ServicoResponse> listarTodos(){
        return servicoRepository.findAll()
        .stream()
        .map(ServicoMapper::toResponse)
        .toList();

    }

    @Transactional
    public ServicoResponse atualizar(Integer id, ServicoRequest request){

        Servico servico = servicoRepository.findById(id)
        .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com id: " + id));

        servico.setNome(request.nome());
        servico.setDescricao(request.descricao());
        servico.setPreco(request.preco());
        servico.setDuracaoMinutos(request.duracaoMinutos());

        Servico servicoAtualizado = servicoRepository.save(servico);
        return ServicoMapper.toResponse(servicoAtualizado);
    }

    @Transactional
    public void deletar(Integer id){
        if(!servicoRepository.existsById(id)){
         throw new RecursoNaoEncontradoException("Serviço não encontrado com id : " + id);
        }
 
        servicoRepository.deleteById(id);
 
     }
}