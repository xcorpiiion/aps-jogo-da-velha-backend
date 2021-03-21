package br.com.unip.servidoraps;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

@Getter
@Slf4j
class TratadorMensagemCliente implements Runnable {

    private final Socket cliente;

    private final Servidor servidor;

    public TratadorMensagemCliente(Socket cliente, Servidor servidor) {
        this.cliente = cliente;
        this.servidor = servidor;
    }

    public void run() {
        try (Scanner scanner = new Scanner(this.cliente.getInputStream())) {
            while (scanner.hasNextLine()) {
                servidor.distribuiMensagem(this.cliente, scanner.nextLine());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
        }
    }
}
