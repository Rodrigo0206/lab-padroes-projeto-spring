package one.digitalinnovation.gof.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import one.digitalinnovation.gof.model.*;
import one.digitalinnovation.gof.service.ViaCepService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private ViaCepService viaCepService;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveInserirClienteComEnderecoNovo() {
        // Arrange
        String cep = "01001-000";
        Endereco endereco = Endereco.builder()
                .cep(cep)
                .logradouro("Praça da Sé")
                .localidade("São Paulo")
                .uf("SP")
                .build();

        Cliente cliente = new Cliente(null, "João da Silva", endereco);

        when(enderecoRepository.findById(cep)).thenReturn(Optional.empty());
        when(viaCepService.consultarCep(cep)).thenReturn(endereco);

        // Act
        clienteService.inserir(cliente);

        // Assert
        verify(enderecoRepository).save(endereco);
        verify(clienteRepository).save(cliente);
        assertEquals(cep, cliente.getEndereco().getCep());
    }
}
