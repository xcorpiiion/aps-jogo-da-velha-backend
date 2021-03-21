package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Slf4j
@Getter
@Setter
@NoArgsConstructor
@Component
public class Servidor {

    private int porta;

    private List<Socket> clientes;

    public void executa() throws IOException {
        try (ServerSocket servidor = new ServerSocket(this.porta)) {
            log.info("Servidor conectado na porta {}", this.getPorta());

            while (true) {
                Socket cliente = servidor.accept();
                log.info("Nova conexão com o cliente {}", cliente.getInetAddress().getHostAddress());
                this.clientes.add(cliente);
                TratadorMensagemCliente tratadorMensagem = new TratadorMensagemCliente(cliente, this);
                new Thread(tratadorMensagem).start();
            }
        }
    }

    public void distribuiMensagem(Socket clienteQueEnviou, String mensagem) {
        for (Socket cliente : this.clientes) {
            if (!cliente.equals(clienteQueEnviou)) {
                try {
                    log.info("Nova mensagem...");
                    PrintStream printStream = new PrintStream(cliente.getOutputStream());
                    log.info("Enviando mensagem...");
                    printStream.println(mensagem);
                } catch (IOException e) {
                    log.info(e.getMessage(), e.getCause());
                }
            }
        }
    }
}
