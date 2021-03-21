package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class Servidor {

    private ExecutorService threadPool;

    private ServerSocket servidor;

    public void iniciaServidor() {
        this.criacaoServidor();
        try {
            this.rodar(servidor, threadPool);
        } catch (IOException e) {
            log.info(e.getMessage(), e.getCause());
        }
    }

    private void criacaoServidor() {
        try {
            log.info("iniciando o servidor...");
            this.setServidor(new ServerSocket(12345));
            this.setThreadPool(newFixedThreadPool(4, new FabricaThread()));
        } catch (IOException e) {
            log.info(e.getMessage(), e.getCause());
        }
    }

    private void rodar(ServerSocket servidor, ExecutorService threadPool) throws IOException {
        while (true) {
            Socket socket = servidor.accept();
            log.info("Cliente conectado na porta {}", socket.getPort());
            DistribuirTarefas distribuirTarefas = new DistribuirTarefas(socket, this, threadPool);
            threadPool.execute(distribuirTarefas);
        }
    }


}
