package br.com.unip.jogodavelhaaps.cliente;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

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

    public static synchronized ClientePassivoEscravo getInstance() {
        if (cliente == null) {
            cliente = new ClientePassivoEscravo("127.0.0.1", 11111, "Login");
        }
        return cliente;
    }
}
