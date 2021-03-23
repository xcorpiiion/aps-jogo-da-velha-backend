package br.com.unip.aps.jogo_velha;

import br.com.unip.aps.jogo_velha.cliente_chat.ClienteChat;
import br.com.unip.aps.jogo_velha.tela.TelaJogo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Getter
@Slf4j
@SpringBootApplication
public class LoginApplication implements CommandLineRunner {

    @Autowired
    private ClienteChat clienteChat;

    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        this.getTelaJogo().init();
    }
}
