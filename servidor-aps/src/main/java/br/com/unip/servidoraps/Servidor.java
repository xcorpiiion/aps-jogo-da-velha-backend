package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class Servidor {

    private ServerSocket servidor;

    private static final Map<String, DistribuirTarefas> clientesConectados = new HashMap<>();

    public void iniciaServidor() {
        this.criacaoServidor();
        try {
            this.rodar(servidor);
        } catch (IOException e) {
            log.info(e.getMessage(), e.getCause());
        }
    }

    private void criacaoServidor() {
        try {
            log.info("iniciando o servidor...");
            this.setServidor(new ServerSocket(12345));
        } catch (IOException e) {
            log.info(e.getMessage(), e.getCause());
        }
    }

    private void rodar(ServerSocket servidor) throws IOException {
        while (true) {
            Socket socket = servidor.accept();
            log.info("Cliente conectado na porta {}", socket.getPort());
            new DistribuirTarefas(socket);
        }
    }

    public static Map<String, DistribuirTarefas> getClientesConectados() {
        return clientesConectados;
    }
}
