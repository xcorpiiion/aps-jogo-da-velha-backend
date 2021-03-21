package br.com.unip.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.System.in;
import static java.lang.System.out;

@Slf4j
@Getter
@Component
public class Cliente {

    private Thread threadSendToServer;

    private Thread threadreceiveFromServer;

    @Setter
    private Socket socket;

    public void criacaoCliente() {
        try {
            this.setSocket(new Socket("localhost", 12345));
            log.info("Cliente conectado");
            this.escutar();
            this.sendMessageToServer(socket);
        } catch (Exception e) {
            log.info(e.getMessage(), e.getCause());
        }
    }

    private void esperaConexaoFechar() throws InterruptedException {
        this.getThreadSendToServer().join();
        log.info("Fechando conexão do cliente...");
    }

    private void sendMessageToServer(Socket socket) {
        threadSendToServer = new Thread(() -> {
            PrintStream saida = null;
            try {
                saida = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                log.info(e.getMessage(), e.getCause());
            }
            Scanner teclado = new Scanner(in);

            out.println("Informe o seu id: ");
            String id = teclado.nextLine();
            out.println("Digite sua mensagem: ");
            String mensagem = teclado.nextLine();
            out.println("Digite id de destino: ");
            String targetId = teclado.nextLine();

            teclado.close();
            if (saida != null) {
                saida.println("meuid:" + id);
                final StringBuilder msg = new StringBuilder();
                saida.println(msg.append(mensagem).append(" - ").append(targetId).toString());
                saida.close();
            }
        });
        threadSendToServer.start();
    }

    private void receiveMessageFromServer(Socket socket) {
        threadreceiveFromServer = new Thread(() -> {
            Scanner resposta = null;
            try {
                resposta = new Scanner(socket.getInputStream());
            } catch (IOException e) {
                log.info(e.getMessage(), e.getCause());
            }
            if (resposta != null) {
                while (resposta.hasNextLine()) {
                    log.info("Recebendo mensagem do servidor...");
                    log.info(resposta.nextLine());
                }
                resposta.close();
            }
        });
        threadreceiveFromServer.start();
    }

    public void escutar() {
        Thread escutar = new Thread(() -> {
            Scanner entrada = null;
            try {
                entrada = new Scanner(this.getSocket().getInputStream());
            } catch (IOException e) {
                log.error(e.getMessage(), e.getCause());
            }
            log.info("Cliente está escutando... ");
            if (entrada != null) {
                while (entrada.hasNextLine()) {
                    log.info("Nova mensagem: {}", entrada.nextLine());
                }
            }
        });
        escutar.setName("Cliente");
        escutar.start();
    }

}
