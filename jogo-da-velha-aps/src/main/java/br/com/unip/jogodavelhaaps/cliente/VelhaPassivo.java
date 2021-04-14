package br.com.unip.jogodavelhaaps.cliente;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import static java.awt.BorderLayout.CENTER;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.UIManager.getColor;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Getter
public class VelhaPassivo extends JFrame {

    private final int[][] matrizVelha = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    private JPanel contentPane;

    private final JButton button00 = new JButton(EMPTY);

    private final JButton button01 = new JButton(EMPTY);

    private final JButton button02 = new JButton(EMPTY);

    private final JButton button10 = new JButton(EMPTY);

    private final JButton button11 = new JButton(EMPTY);

    private final JButton button12 = new JButton(EMPTY);

    private final JButton button20 = new JButton(EMPTY);

    private final JButton button21 = new JButton(EMPTY);

    private final JButton button22 = new JButton(EMPTY);

    private final JButton[][] matrizVelhaBotao = {
            {button00, button01, button02},
            {button10, button11, button12},
            {button20, button21, button22}
    };

    private String nomeJogador1 = "";

    private String nomeJogador2 = "";

    private int idJogador1;

    private int idJogador2;

    private final JLabel lblNomeJogador = new JLabel();

    private final JLabel lblNomeJogo = new JLabel();

    public VelhaPassivo(String loginJogador1, String idJogador1, String loginJogador2, String idJogador2) {
        this.criaTamanhoTela();
        this.criaContentPane();
        JLayeredPane layeredPane = this.criaLayeredPane();
        this.colocaNomeDoJogadorNaTela(loginJogador1, idJogador1, loginJogador2, idJogador2);
        this.desabilitaBotoes();
        this.definePosicaoBotao(layeredPane);
        this.definePosicaoLabel(layeredPane);
        this.criaPanel(layeredPane);
    }

    private JLayeredPane criaLayeredPane() {
        JLayeredPane layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, CENTER);
        return layeredPane;
    }

    private void criaPanel(JLayeredPane layeredPane) {
        JPanel panel = new JPanel();
        panel.setBackground(getColor("Button.light"));
        panel.setBounds(10, 57, 404, 194);
        layeredPane.add(panel);
    }

    private void definePosicaoLabel(JLayeredPane layeredPane) {
        JLabel lblJogador = new JLabel("Vez:");
        lblJogador.setBounds(10, 11, 78, 14);
        layeredPane.add(lblJogador);

        lblNomeJogador.setBounds(98, 11, 229, 14);
        layeredPane.add(lblNomeJogador);

        JLabel lblJogo = new JLabel("Jogo:");
        lblJogo.setBounds(10, 32, 78, 14);
        layeredPane.add(lblJogo);

        lblNomeJogo.setBounds(98, 32, 229, 14);
        layeredPane.add(lblNomeJogo);
    }

    private void definePosicaoBotao(JLayeredPane layeredPane) {
        button00.setBounds(125, 72, 50, 50);
        layeredPane.add(button00);

        button10.setBounds(125, 133, 50, 50);
        layeredPane.add(button10);

        button20.setBounds(125, 190, 50, 50);
        layeredPane.add(button20);

        button11.setBounds(185, 133, 50, 50);
        layeredPane.add(button11);

        button01.setBounds(185, 72, 50, 50);
        layeredPane.add(button01);

        button21.setBounds(185, 190, 50, 50);
        layeredPane.add(button21);

        button02.setBounds(245, 72, 50, 50);
        layeredPane.add(button02);

        button12.setBounds(245, 133, 50, 50);
        layeredPane.add(button12);

        button22.setBounds(245, 190, 50, 50);
        layeredPane.add(button22);
    }

    private void colocaNomeDoJogadorNaTela(String loginJogador1, String idJogador1, String loginJogador2, String idJogador2) {
        this.nomeJogador1 = loginJogador1;
        this.nomeJogador2 = loginJogador2;
        this.idJogador1 = Integer.parseInt(idJogador1);
        this.idJogador2 = Integer.parseInt(idJogador2);

        if (this.idJogador1 < this.idJogador2) {
            lblNomeJogador.setText(this.nomeJogador1);
        } else {
            lblNomeJogador.setText(this.nomeJogador2);
        }

        lblNomeJogo.setText(this.nomeJogador1 + " X " + this.nomeJogador2);
    }

    private void criaContentPane() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    private void criaTamanhoTela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 455, 318);
    }

    public void desabilitaBotoes() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.matrizVelhaBotao[i][j].setEnabled(false);
            }
        }
    }

    public int verificaMatriz() {
        int retornoColunas;
        int retornoLinhas;
        int retornoDiagonais;
        retornoColunas = checaColunas();
        retornoLinhas = checaLinhas();
        retornoDiagonais = checaDiagonais();
        if (retornoColunas == -1 || retornoLinhas == -1 || retornoDiagonais == -1) {
            return -1;
        }
        if (retornoColunas == 1 || retornoLinhas == 1 || retornoDiagonais == 1) {
            return 1;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrizVelha[i][j] == 0) {
                    return 0;
                }
            }
        }
        limparVelha();
        showMessageDialog(null, "Jogo empatou!");
        return 2;
    }

    public int checaLinhas() {
        for (int linha = 0; linha < 3; linha++) {

            if ((this.matrizVelha[linha][0] + this.matrizVelha[linha][1] + this.matrizVelha[linha][2]) == -3)
                return -1;
            if ((this.matrizVelha[linha][0] + this.matrizVelha[linha][1] + this.matrizVelha[linha][2]) == 3)
                return 1;
        }

        return 0;

    }

    public int checaColunas() {
        for (int coluna = 0; coluna < 3; coluna++) {
            if ((this.matrizVelha[0][coluna] + this.matrizVelha[1][coluna] + this.matrizVelha[2][coluna]) == -3) {
                return -1;
            }
            if ((this.matrizVelha[0][coluna] + this.matrizVelha[1][coluna] + this.matrizVelha[2][coluna]) == 3) {
                return 1;
            }
        }

        return 0;

    }

    public int checaDiagonais() {
        if ((this.matrizVelha[0][0] + this.matrizVelha[1][1] + this.matrizVelha[2][2]) == -3) {
            return -1;
        }
        if ((this.matrizVelha[0][0] + this.matrizVelha[1][1] + this.matrizVelha[2][2]) == 3) {
            return 1;
        }
        if ((this.matrizVelha[0][2] + this.matrizVelha[1][1] + this.matrizVelha[2][0]) == -3) {
            return -1;
        }
        if ((this.matrizVelha[0][2] + this.matrizVelha[1][1] + this.matrizVelha[2][0]) == 3) {
            return 1;
        }
        return 0;
    }

    public void limparVelha() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                matrizVelha[i][j] = 0;
            }
        }
        button00.setText("");
        button01.setText("");
        button02.setText("");
        button10.setText("");
        button11.setText("");
        button12.setText("");
        button20.setText("");
        button21.setText("");
        button22.setText("");
    }
}
