package br.com.unip.aps.jogo_velha;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;

@Setter
@Getter
@Slf4j
public class ClientePassivoEscravo {

    private final String host;

    private final int porta;

    private Socket socketCliente;

    private PrintStream saida;

    private String login;

    private String idJogador1;

    private String idJogador2;

    private String loginJogador1;

    private String loginJogador2;

    @Setter
    private static ClientePassivoEscravo cliente;

    public ClientePassivoEscravo(String host, int porta, String login) {
        this.host = host;
        this.porta = porta;
        this.login = login;
        setCliente(this);
    }

//    public void executa() throws IOException {
//        this.socketCliente = new Socket(this.host, this.porta);
//        log.info("O cliente se conectou ao servidor!");
//        InputStream inputStream = this.socketCliente.getInputStream();
//        Recebedor recebedor = new Recebedor(inputStream);
//        Thread threadRecebedor = new Thread(recebedor);
//        threadRecebedor.start();
//    }
//
//    public void enviaDados(String dado) throws IOException {
//        // lê msgs do teclado e manda pro servidor
//        this.saida = new PrintStream(this.socketCliente.getOutputStream());
//
//        this.saida.println(dado);
//    }
//
//    public void fechaConexao() throws IOException {
//        this.saida.close();
//        this.socketCliente.close();
//    }

    public static synchronized ClientePassivoEscravo getInstance() {
        if (cliente == null) {
            cliente = new ClientePassivoEscravo("127.0.0.1", 11111, "Login");
        }
        return cliente;
    }
}
