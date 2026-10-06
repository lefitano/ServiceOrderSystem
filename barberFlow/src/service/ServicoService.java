package com.barberflow.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.barberflow.repository.ServicoRepository;
import com.barberflow.model.Servico;
import com.barberflow.mapper.ServicoMapper;
import com.barberflow.dto.ServicoRequest;
import com.barberflow.dto.ServicoResponse;
import com.barberflow.exception.RecursoNaoEncontradoException;
import java.util.List;


@Service
@RequiredArgsConstructor 
public class ServicoService {
    private final ServicoRepository servicoRepository;


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

    public void deletar(Integer id){
        if(!servicoRepository.existsById(id)){
         throw new RecursoNaoEncontradoException("Serviço não encontrado com id : " + id);
        }
 
        servicoRepository.deleteById(id);
 
     }
}