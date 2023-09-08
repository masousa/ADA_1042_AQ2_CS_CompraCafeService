package tech.ada.bootcamp.cafe.loaddata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.ada.bootcamp.cafe.entidades.Combo;
import tech.ada.bootcamp.cafe.repository.ComboRepository;
import tech.ada.bootcamp.cafe.repository.ItemRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class ComboLoadData {

    public static final String TODOS_OS_CAFÉS = "Todos os cafés";
    private final ItemRepository itemRepository;
    private final ComboRepository comboRepository;
    private static String IDENTIFICADOR_COMBO = "a9c6fd11-b88d-4cda-b945-881f6df6958c";

    public void run() {
        if (!comboRepository.existsByDescricao(TODOS_OS_CAFÉS)) {
            createDefaultCombo();
        }
    }

    private void createDefaultCombo() {
        Combo combo = new Combo();
        combo.setDescricao(TODOS_OS_CAFÉS);
        combo.setValorFinal(50.0);
        combo.setItems(itemRepository.findAll());
        combo.setIdentificador(IDENTIFICADOR_COMBO);
        log.info("Combo salvo {}", combo);
        comboRepository.save(combo);
    }
}
