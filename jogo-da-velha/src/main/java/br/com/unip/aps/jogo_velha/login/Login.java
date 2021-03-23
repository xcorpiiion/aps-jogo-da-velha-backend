package br.com.unip.aps.jogo_velha.login;

import br.com.unip.aps.jogo_velha.Cliente;
import br.com.unip.aps.jogo_velha.login.cliente_chat.ClienteChat;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static java.awt.EventQueue.invokeLater;
import static java.lang.Integer.parseInt;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.JOptionPane.showMessageDialog;

@Getter
@Slf4j
public class Login extends JFrame {

    private final JPanel contentPane;

    @Setter
    private static JTextField txtNomeJogador;

    private final JButton btnJogar;

    private final JLabel lblEntreComSeu;

    private final JButton btnOlhar;

    private final JLayeredPane layeredPane;

    private final JLabel lblCarregando;

    private Cliente cliente;

    private JTextField ipServidor;

    private JTextField portaServidor;

    public static void main(String[] args) {
        invokeLater(() -> {
            try {
                Login frame = new Login();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public Login() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        layeredPane = new JLayeredPane();
        contentPane.add(layeredPane, BorderLayout.CENTER);

        lblEntreComSeu = new JLabel("Entre com seu apelido:");
        lblEntreComSeu.setBounds(10, 11, 167, 14);
        layeredPane.add(lblEntreComSeu);

        lblCarregando = new JLabel("Aguardando um oponente se conectar...");
        lblCarregando.setVisible(false);
        lblCarregando.setBounds(10, 226, 246, 14);
        layeredPane.add(lblCarregando);

        setTxtNomeJogador(new JTextField());
        txtNomeJogador.setBounds(10, 36, 404, 20);
        layeredPane.add(txtNomeJogador);
        txtNomeJogador.setColumns(10);

        btnJogar = new JButton("Jogar");
        btnJogar.setBounds(325, 188, 89, 23);
        layeredPane.add(btnJogar);

        btnJogar.addActionListener(arg0 -> {
            try {
                cliente = new Cliente(ipServidor.getText(), parseInt(portaServidor.getText()), txtNomeJogador.getText());
                cliente.executa();
                cliente.enviaDados("logar;" + txtNomeJogador.getText());

                lblCarregando.setVisible(true);
                showMessageDialog(null, "Conectado !");
                new Thread(() -> {
                    ClienteChat clienteChat = new ClienteChat();
                    clienteChat.setHost(ipServidor.getText());
                    clienteChat.setPorta(12345);
                    try {
                        clienteChat.executa();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            } catch (IOException e) {
                lblCarregando.setVisible(false);
                showMessageDialog(null, "Erro na conexção!");
                e.printStackTrace();
            }
        });

        btnOlhar = new JButton("Olhar");
        btnOlhar.setBounds(226, 188, 89, 23);
        layeredPane.add(btnOlhar);

        ipServidor = new JTextField();
        ipServidor.setColumns(10);
        ipServidor.setBounds(10, 92, 404, 20);
        layeredPane.add(ipServidor);

        JLabel lblEntreComIp = new JLabel("Entre com ip do servidor:");
        lblEntreComIp.setBounds(10, 67, 167, 14);
        layeredPane.add(lblEntreComIp);

        portaServidor = new JTextField();
        portaServidor.setColumns(10);
        portaServidor.setBounds(10, 157, 404, 20);
        layeredPane.add(portaServidor);

        JLabel lblEntreComA = new JLabel("Entre com a porta:");
        lblEntreComA.setBounds(10, 132, 167, 14);
        layeredPane.add(lblEntreComA);

        btnOlhar.addActionListener(actionEvent -> {
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
}
