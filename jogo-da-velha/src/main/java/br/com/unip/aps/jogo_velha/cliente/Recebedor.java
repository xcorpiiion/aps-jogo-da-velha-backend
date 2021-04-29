package br.com.unip.aps.jogo_velha.cliente;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showMessageDialog;

@Slf4j
public class Recebedor implements Runnable {

    private Velha velha;

    private final InputStream servidor;

    public Recebedor(InputStream servidor) {
        this.servidor = servidor;
    }

    public void run() {
        Scanner scanner = new Scanner(servidor);
        while (scanner.hasNextLine()) {
            String entrada = scanner.nextLine();
            this.trataEntrada(entrada);
            log.info(entrada);
        }
    }

    public void trataEntrada(String entrada) {
        String[] array = entrada.split(";");
        switch (array[0]) {
            case "logar":
                //Id, loginOponente, idOponente
                logar(array[1], array[2], array[3]);
                break;
            case "jogar":
                jogar(array[1], array[2]);
                break;
            default:
                break;
        }
    }

    public void logar(String id, String loginOponente, String idOponente) {
        Cliente cliente = Cliente.getInstance();
        cliente.setId(id);
        cliente.setIdOponente(idOponente);
        log.info("ClienteLongin: {}", loginOponente);
        log.info("MeuloginRecebedor: {}", cliente.getLogin());
        this.velha = new Velha(cliente.getLogin(), loginOponente, cliente);
        this.velha.show();
    }

    public void jogar(String linha, String coluna) {
        this.velha.getMatrizVelhaBotao()[parseInt(linha)][parseInt(coluna)].setText("X");
        this.velha.getMatrizVelha()[parseInt(linha)][parseInt(coluna)] = -1;
        int retorno = this.velha.verificaMatriz();
        this.verificaQuemVenceu(retorno, this.velha.getNomeJogador(), this.velha.getNomeOponente());
        this.velha.habilitaBotoes();
    }

    private void verificaQuemVenceu(int retorno, String nomeJogador, String nomeOponente) {
        if (retorno == 1) {
            log.info("Jogador {} venceu", nomeJogador);
            showMessageDialog(null, nomeJogador + " venceu o jogo!");
            this.velha.limparVelha();
        } else if (retorno == -1) {
            log.info("Jogador {} venceu", nomeJogador);
            showMessageDialog(null, nomeOponente + " venceu o jogo!");
            this.velha.limparVelha();
        }
    }
}