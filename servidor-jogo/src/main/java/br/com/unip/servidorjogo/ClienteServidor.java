package br.com.unip.servidorjogo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ClienteServidor {

    private String login;

    private int loginId;

    private PrintStream loginPS;

    private String oponente;

    private int oponenteId;

    private PrintStream oponentePS;

    private int ativo;

    private final List<ClientePassivo> clientePassivo = new ArrayList<>();

    public void setClientePassivo(ClientePassivo cliente) {
        this.clientePassivo.add(cliente);
    }

    public List<ClientePassivo> getClientePassivo() {
        return this.clientePassivo;
    }
}
