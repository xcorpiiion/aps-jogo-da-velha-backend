package br.com.unip.aps.jogo_velha;

import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Scanner;

import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showMessageDialog;

@Slf4j
public class Recebedor implements Runnable {

    private Velha velha;

    private VelhaPassivo velhaPassivo;

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
            case "logarPassivo":
                //loginJogador1, idloginJogador1, loginJogador2, idloginJogador2
                logarPassivo(array[1], array[2], array[3], array[4]);
                break;
            case "jogar":
                jogar(array[1], array[2]);
                break;
            case "jogarPassivo":
                //idJogador, linha, coluna
                jogarPassivo(array[1], array[2], array[3]);
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

    public void logarPassivo(String loginJogador1, String idJogador1, String loginJogador2, String idJogador2) {
        ClientePassivoEscravo clientePassivo = ClientePassivoEscravo.getInstance();
        clientePassivo.setIdJogador1(idJogador1);
        clientePassivo.setIdJogador2(idJogador2);
        clientePassivo.setLoginJogador1(idJogador1);
        clientePassivo.setLoginJogador2(idJogador2);
        this.velhaPassivo = new VelhaPassivo(loginJogador1, idJogador1, loginJogador2, idJogador2);
        this.velhaPassivo.show();
    }

    public void jogar(String linha, String coluna) {
        this.velha.matrizVelhaBotao[parseInt(linha)][parseInt(coluna)].setText("X");
        this.velha.matrizVelha[parseInt(linha)][parseInt(coluna)] = -1;
        int retorno = this.velha.verificaMatriz();
        verificaQuemVenceu(retorno, this.velha.nomeJogador, this.velha.nomeOponente);
        this.velha.habilitaBotoes();
    }

    public void jogarPassivo(String idJogador, String linha, String coluna) {
        if (this.velhaPassivo.idJogador1 == parseInt(idJogador)) {
            this.velhaPassivo.matrizVelhaBotao[parseInt(linha)][parseInt(coluna)].setText("X");
            this.velhaPassivo.matrizVelha[parseInt(linha)][parseInt(coluna)] = 1;
            this.velhaPassivo.lblNomeJogador.setText(this.velhaPassivo.nomeJogador2);
        } else {
            this.velhaPassivo.matrizVelhaBotao[parseInt(linha)][parseInt(coluna)].setText("O");
            this.velhaPassivo.matrizVelha[parseInt(linha)][parseInt(coluna)] = -1;
            this.velhaPassivo.lblNomeJogador.setText(this.velhaPassivo.nomeJogador1);
        }
        int retorno = this.velhaPassivo.verificaMatriz();
        verificaQuemVenceu(retorno, this.velhaPassivo.nomeJogador1, this.velhaPassivo.nomeJogador2);
    }

    private void verificaQuemVenceu(int retorno, String nomeJogador, String nomeOponente) {
        if (retorno == 1) {
            showMessageDialog(null, nomeJogador + " venceu o jogo!");
            this.velha.limparVelha();
        } else if (retorno == -1) {
            showMessageDialog(null, nomeOponente + " venceu o jogo!");
            this.velha.limparVelha();
        }
    }
}