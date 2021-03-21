package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

@Slf4j
public class DistribuirTarefas implements Runnable {

    @Getter
    private final Socket socket;

    @Getter
    private Servidor servidor;

    @Getter
    private ExecutorService threadPool;

    public DistribuirTarefas(Socket socket, Servidor servidor, ExecutorService threadPool) {
        this.socket = socket;
        this.servidor = servidor;
        this.threadPool = threadPool;
    }

    @Override
    public void run() {
        log.info("Distribuindo tarefas para {}", this.getSocket());
        try {
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintStream saidaCliente = new PrintStream(this.getSocket().getOutputStream());
            while (scanner.hasNextLine()) {
                String comando = scanner.nextLine();
                log.info("Comando recebido {}", comando);

                log.info(comando);
            }
            scanner.close();
            saidaCliente.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
