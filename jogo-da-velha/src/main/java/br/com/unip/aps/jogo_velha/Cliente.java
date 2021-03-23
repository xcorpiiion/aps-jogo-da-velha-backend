package br.com.unip.aps.jogo_velha;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

@Getter
@Setter
@Slf4j
public class Cliente {

	private String host;

	private int porta;

	private Socket socketCliente;

	private PrintStream saida;

	private String id;

	private String idOponente;

	private String login;

	@Setter
	private static Cliente cliente;


	public Cliente (String host, int porta, String login) {
		this.host = host;
		this.porta = porta;
		this.login = login;
		setCliente(this);
	}

	public void executa() throws UnknownHostException, IOException {
		this.socketCliente = new Socket(this.host, this.porta);
		log.info("O cliente se conectou ao servidor!");
		InputStream inputStream = this.socketCliente.getInputStream();
		Recebedor recebedor = new Recebedor(inputStream);
		Thread threadRecebedor = new Thread(recebedor);
		threadRecebedor.start();
	}

	public void enviaDados(String dado) throws IOException {
		// lê msgs do teclado e manda pro servidor
		this.saida = new PrintStream(this.socketCliente.getOutputStream());

		this.saida.println(dado);
	}

	public void fechaConexao() throws IOException{
		this.saida.close();
		this.socketCliente.close();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdOponente() {
		return idOponente;
	}

	public void setIdOponente(String idOponente) {
		this.idOponente = idOponente;
	}

	public static synchronized Cliente getInstance()
	{
		if (cliente == null) {
			cliente = new Cliente("127.0.0.1", 11111, "Login");
		}
		return cliente;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
