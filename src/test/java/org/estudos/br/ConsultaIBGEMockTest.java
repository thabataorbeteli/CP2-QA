package org.estudos.br;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsultaIBGEMockitoTest {

    @Test
    @DisplayName("Teste para consulta de estado com Mockito")
    public void testConsultarEstadoComMockito() throws IOException {
        // Arrange
        String uf = "SP"; // Define o estado a ser consultado
        HttpURLConnection connectionMock = mock(HttpURLConnection.class);
        when(connectionMock.getResponseCode()).thenReturn(200);
        when(connectionMock.getInputStream()).thenReturn(new TestInputStream("Teste de resposta"));

        URL urlMock = mock(URL.class);
        when(urlMock.openConnection()).thenReturn(connectionMock);

        // Act
        String resposta = ConsultaIBGE.consultarEstado(uf); // Chama o método a ser testado

        // Assert
        // Verifica se a resposta não está vazia
        assert !resposta.isEmpty();

        // Verifica se o status code é 200 (OK)
        HttpURLConnection connection = (HttpURLConnection) new URL(ConsultaIBGE.ESTADOS_API_URL + uf).openConnection();
        int statusCode = connection.getResponseCode();
        assertEquals(200, statusCode, "O status code da resposta da API deve ser 200 (OK)");
    }

    static class TestInputStream extends java.io.InputStream {
        private ByteArrayInputStream stream;

        public TestInputStream(String string) {
            this.stream = new ByteArrayInputStream(string.getBytes());
        }

        @Override
        public int read() {
            return stream.read();
        }
    }
}
