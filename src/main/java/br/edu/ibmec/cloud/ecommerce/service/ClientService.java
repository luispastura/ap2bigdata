package br.edu.ibmec.cloud.ecommerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.cosmos.implementation.guava25.base.Optional;
import com.azure.cosmos.models.PartitionKey;

import br.edu.ibmec.cloud.ecommerce.entity.Client;
import br.edu.ibmec.cloud.ecommerce.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clienteRepository;

    public void save(Client cliente) {
        cliente.setClienteId(UUID.randomUUID().toString());
        this.clienteRepository.save(cliente);
    }

    public java.util.Optional<Client> findById(String clienteId) {
        return this.clienteRepository.findById(clienteId);
    }

    public List<Client> findByNome(String nome) {
        return this.clienteRepository.findByNome(nome);
    }

    public List<Client> findByRegiao(String regiao) {
        return this.clienteRepository.findByRegiao(regiao);
    }

    public void delete(String clienteId) throws Exception {
        java.util.Optional<Client> optCliente = this.clienteRepository.findById(clienteId);

        if (!optCliente.isPresent()) {
            throw new Exception("Cliente não encontrado para exclusão");
        }

        this.clienteRepository.deleteById(clienteId, new PartitionKey(optCliente.get().getRegiao()));
    }

    public void update(String clienteId, Client clienteAtualizado) throws Exception {
        java.util.Optional<Client> optCliente = this.clienteRepository.findById(clienteId);

        if (!optCliente.isPresent()) {
            throw new Exception("Cliente não encontrado para atualização");
        }

        Client clienteExistente = optCliente.get();
        clienteAtualizado.setClienteId(clienteExistente.getClienteId());
        clienteAtualizado.setRegiao(clienteExistente.getRegiao());
        this.clienteRepository.save(clienteAtualizado);
    }
}
