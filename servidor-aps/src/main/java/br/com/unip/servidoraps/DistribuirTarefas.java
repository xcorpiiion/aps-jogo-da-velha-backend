package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import static br.com.unip.servidoraps.Servidor.getClientesConectados;

@Slf4j
@Getter
@Setter
public class DistribuirTarefas {

    private final Socket socket;

    private String id;

    public DistribuirTarefas(Socket socket) {
        this.socket = socket;
        this.init();
    }

    public void init() {
        this.escutar();
    }

    private void escutar() {
        Thread threadEscutar = new Thread(() -> {
            try {
                log.info("Cliente esperando nova mensagem...");
                Scanner scanner = new Scanner(socket.getInputStream());
                verificaSeTemNovaMensagem(scanner);
                scanner.close();
            } catch (IOException e) {
                log.info(e.getMessage(), e.getCause());
            }
        });
        threadEscutar.setName("Escutar");
        threadEscutar.start();
    }

    private void verificaSeTemNovaMensagem(Scanner scanner) throws IOException {
        while (scanner.hasNextLine()) {
            log.info("Nova mensagem recebida...");
            String mensagem = scanner.nextLine();
            log.info("Verificando id...");
            if (verificaId(mensagem)) {
                continue;
            }
            String[] mensagens = formataTextoMensagem(mensagem);
            mostraMensagem(mensagens);
        }
    }

    private void mostraMensagem(String[] mensagens) {
        log.info("De: {}", this.getId());
        log.info("Para: {}", mensagens[0]);
        log.info("Mensagem: {}", mensagens[1]);
    }

    private String[] formataTextoMensagem(String mensagem) throws IOException {
        String[] mensagens = mensagem.split(" - ");
        enviarMensagem(mensagens[1], mensagens[0]);
        return mensagens;
    }

    private boolean verificaId(String mensagem) {
        // id irá vim no seguinte formato -> meuid:'aqui_ira_ficar_o_ID'
        if (mensagem.contains("meuid:")) {
            this.setId(mensagem.substring("meuid:".length()));
            getClientesConectados().put(this.getId(), this);
            log.info("Cliente conectado...");
            return true;
        }
        return false;
    }

    public void enviarMensagem(String idDestino, String mensagem) throws IOException {
        if (getClientesConectados().get(idDestino) != null) {
            DistribuirTarefas cliente = getClientesConectados().get(idDestino);
            cliente.escrever(mensagem);
        } else {
            log.info("Usuario está off");
        }
    }

    public void escrever(String mensagem) throws IOException {
        PrintStream saida = new PrintStream(this.socket.getOutputStream());
        saida.println(mensagem);
    }
}
