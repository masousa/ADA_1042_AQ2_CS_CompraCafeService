package tech.ada.bootcamp.cafe.loaddata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.ada.bootcamp.cafe.entidades.Usuario;
import tech.ada.bootcamp.cafe.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioLoadData implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private static String IDENTIFICADOR_USUARIO = "0a3abee5-ea4a-4306-95d2-d8aa343abd1a";

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() <= 0) {
            createDefaultUsuario();
        }

    }


    private void createDefaultUsuario() {
        Usuario usuario = new Usuario();
        usuario.setCpf("00000000000");
        usuario.setNome("Jose Joao da Silva");
        usuario.setDesconto(50);
        usuario.setIdentificador(IDENTIFICADOR_USUARIO);
        log.info("Usuario Salvo {}", usuario);
        usuarioRepository.save(usuario);
    }
}
