package br.com.unip.servidoraps;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServidorApsApplication implements CommandLineRunner {

	@Getter
	@Autowired
	private Servidor servidor;

	public static void main(String[] args) {
		SpringApplication.run(ServidorApsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.servidor.iniciaServidor();
	}
}
