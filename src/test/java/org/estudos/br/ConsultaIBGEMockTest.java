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

    @BeforeEach
    public void setup() throws IOException {
        // Inicializa os mocks
        MockitoAnnotations.openMocks(this);

        // Configura o comportamento do mock para retornar o JSON de resposta para estado com sigla "RO"
        InputStream inputStreamStateRO = new ByteArrayInputStream(JSON_RESPONSE_STATE.getBytes());
        when(connectionMock.getInputStream()).thenReturn(inputStreamStateRO);
    }

    @Test
    @DisplayName("Consulta de Estado - Verificar Sigla")
    public void testConsultarEstadoSiglaComMock() throws IOException {
        // Sigla do estado a ser consultado
        String estadoUf = "RO";

        // Act (Execução do método a ser testado)
        String response = ConsultaIBGE.consultarEstado(estadoUf);

        // Verificamos se o JSON retornado contém a sigla correta
        assertEquals(JSON_RESPONSE_STATE, response, "O JSON retornado não corresponde ao esperado.");
    }
}
