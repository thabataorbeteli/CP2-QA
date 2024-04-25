package org.estudos.br;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ConsultaIBGEMockTest {
    @Mock
    private HttpURLConnection connectionMock;

    // JSON de resposta simulada para estado com sigla "RO"
    private static final String JSON_RESPONSE_STATE = "{\"id\":11,\"sigla\":\"RO\",\"nome\":\"Rondônia\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}";

    // JSON de resposta simulada para distrito com ID 160030312
    private static final String JSON_RESPONSE_DISTRICT = "[{\"id\":160030312,\"nome\":\"Fazendinha\",\"municipio\":{\"id\":1600303,\"nome\":\"Macapá\",\"microrregiao\":{\"id\":16003,\"nome\":\"Macapá\",\"mesorregiao\":{\"id\":1602,\"nome\":\"Sul do Amapá\",\"UF\":{\"id\":16,\"sigla\":\"AP\",\"nome\":\"Amapá\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}}},\"regiao-imediata\":{\"id\":160001,\"nome\":\"Macapá\",\"regiao-intermediaria\":{\"id\":1601,\"nome\":\"Macapá\",\"UF\":{\"id\":16,\"sigla\":\"AP\",\"nome\":\"Amapá\",\"regiao\":{\"id\":1,\"sigla\":\"N\",\"nome\":\"Norte\"}}}}}}]";

    @BeforeEach
    public void setup() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock para retornar o JSON de resposta para estado com sigla "RO"
        InputStream inputStreamStateRO = new ByteArrayInputStream(JSON_RESPONSE_STATE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStreamStateRO);
    }

    //TESTE 1
    @Test
    @DisplayName("Consulta de Estado - Verificar Sigla")
    public void testConsultarEstadoSiglaComMock() throws IOException {
        // Sigla do estado a ser consultado
        String estadoUf = "RO";

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarEstado(estadoUf);

        // Assert (Verifica se o JSON retornado contém a sigla correta)
        assertEquals(JSON_RESPONSE_STATE, response, "O JSON retornado não corresponde ao esperado.");
    }

    //TESTE 2
    @Test
    @DisplayName("Consulta de Distrito - Verificar Resposta")
    public void testConsultarDistrito() throws IOException {
        // Arrange (Preparação dos dados)
        int idDistrito = 160030312;

        // Configura o comportamento do mock para retornar o JSON de resposta para o distrito com ID 160030312
        InputStream inputStreamDistrict = new ByteArrayInputStream(JSON_RESPONSE_DISTRICT.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStreamDistrict);

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarDistrito(idDistrito);

        // Assert (Verifica se a resposta não está vazia)
        assert !response.isEmpty();
    }

}


