package br.com.unip.jogodavelhaaps;

import br.com.unip.jogodavelhaaps.cliente.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JogoDaVelhaApsApplication implements CommandLineRunner {

	@Autowired
	private Cliente telaJogo;

	public static void main(String[] args) {
		SpringApplication.run(JogoDaVelhaApsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		this.telaJogo.init();
	}
}
