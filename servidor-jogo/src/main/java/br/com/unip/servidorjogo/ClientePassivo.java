package br.com.unip.servidorjogo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.PrintStream;

@NoArgsConstructor
@Getter
@Setter
public class ClientePassivo {

    private String login;

    private int loginId;

    private PrintStream loginPS;

    private String jogardor1;

    private int jogador1Id;

    private PrintStream jogador1PS;

    private String jogardor2;

    private int jogador2Id;

    private PrintStream jogador2PS;
}
