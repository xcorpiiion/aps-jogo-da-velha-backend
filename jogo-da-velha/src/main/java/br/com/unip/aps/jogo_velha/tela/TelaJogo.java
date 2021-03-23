package br.com.unip.aps.jogo_velha.tela;

import br.com.unip.aps.jogo_velha.cliente.Cliente;
import br.com.unip.aps.jogo_velha.cliente_chat.ClienteChat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static java.awt.BorderLayout.CENTER;
import static java.awt.EventQueue.invokeLater;
import static java.lang.Integer.parseInt;
import static javax.swing.JOptionPane.showMessageDialog;

@Getter
@Slf4j
@SpringBootApplication
public class TelaJogo extends JFrame implements CommandLineRunner {

    private JPanel contentPane;

    @Setter
    private static JTextField txtNomeJogador;

    private JButton btnJogar;

    private JLabel lblEntreComSeu;

    private JButton btnTestarConexao;

    private JLayeredPane layeredPane;

    private JLabel lblCarregando;

    private Cliente cliente;

    private JTextField ipServidor;

    private JTextField portaServidor;

    public static void main(String[] args) {
        SpringApplication.run(TelaJogo.class);
    }

    @Override
    public void run(String... args) throws Exception {
        this.init();
    }

    public void init() {
        invokeLater(() -> {
            try {
                this.inicio();
                this.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    private void inicio() {
        this.defineTamanhoTela();
        this.criaContentPane();
        this.criaCampoApelido();
        this.criaTextoAguarde();
        this.criaBotaoJogar();
        this.criaCampoIp();
        this.criaCampoPorta();
        this.criaBotaoTestarConexao();
    }

    private void defineTamanhoTela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
    }

    private void criaTextoAguarde() {
        lblCarregando = new JLabel("Aguardando um oponente se conectar...");
        lblCarregando.setVisible(false);
        lblCarregando.setBounds(10, 226, 246, 14);
        layeredPane.add(lblCarregando);
    }

    private void criaCampoApelido() {
        lblEntreComSeu = new JLabel("Entre com seu apelido:");
        lblEntreComSeu.setBounds(10, 11, 167, 14);
        layeredPane.add(lblEntreComSeu);

        setTxtNomeJogador(new JTextField());
        txtNomeJogador.setBounds(10, 36, 404, 20);
        layeredPane.add(txtNomeJogador);
        txtNomeJogador.setColumns(10);
    }

    private void criaContentPane() {
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, CENTER);
    }

    private void criaCampoPorta() {
        portaServidor = new JTextField();
        portaServidor.setColumns(10);
        portaServidor.setBounds(10, 157, 404, 20);
        layeredPane.add(portaServidor);

        JLabel lblPorta = new JLabel("Entre com a porta:");
        lblPorta.setBounds(10, 132, 167, 14);
        layeredPane.add(lblPorta);
    }

    private void criaCampoIp() {
        JLabel lblIp = new JLabel("Entre com ip do servidor:");
        lblIp.setBounds(10, 67, 167, 14);
        layeredPane.add(lblIp);

        ipServidor = new JTextField();
        ipServidor.setColumns(10);
        ipServidor.setBounds(10, 92, 404, 20);
        layeredPane.add(ipServidor);
    }

    private void criaBotaoTestarConexao() {
        btnTestarConexao = new JButton("Testa conexão");
        btnTestarConexao.setBounds(226, 188, 89, 23);
        layeredPane.add(btnTestarConexao);
        onClickTestarConexao();
    }

    private void onClickTestarConexao() {
        btnTestarConexao.addActionListener(actionEvent -> {
            try {
                cliente = new Cliente(ipServidor.getText(), parseInt(portaServidor.getText()), txtNomeJogador.getText());
                cliente.executa();
                cliente.enviaDados("logarPassivo;" + txtNomeJogador.getText());
                lblCarregando.setVisible(true);
                showMessageDialog(null, "Conectado !");
            } catch (IOException e) {
                lblCarregando.setVisible(false);
                showMessageDialog(null, "Erro na conexão!");
                log.error(e.getMessage(), e.getCause());
            }
        });
    }

    private void criaBotaoJogar() {
        btnJogar = new JButton("Jogar");
        btnJogar.setBounds(325, 188, 89, 23);
        layeredPane.add(btnJogar);
        onClickJogar();
    }

    private void onClickJogar() {
        btnJogar.addActionListener(actionEvent -> {
            try {
                cliente = new Cliente(ipServidor.getText(), parseInt(portaServidor.getText()), txtNomeJogador.getText());
                cliente.executa();
                cliente.enviaDados("logar;" + txtNomeJogador.getText());
                lblCarregando.setVisible(true);
                showMessageDialog(null, "Conectado !");
                conectaComServidorChat();
            } catch (IOException e) {
                lblCarregando.setVisible(false);
                showMessageDialog(null, "Erro na conexção!");
                e.printStackTrace();
            }
        });
    }

    private void conectaComServidorChat() {
        Thread threadChat = new Thread(() -> {
            try {
                ClienteChat clienteChat = new ClienteChat();
                clienteChat.setHost(ipServidor.getText());
                clienteChat.setPorta(12345);
                clienteChat.executa();
            } catch (IOException e) {
                log.info(e.getMessage(), e.getCause());
            }

        });
        threadChat.setName("Chat do jogador: " + txtNomeJogador.getName());
        threadChat.start();
    }
}
