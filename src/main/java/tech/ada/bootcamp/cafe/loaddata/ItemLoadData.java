package tech.ada.bootcamp.cafe.loaddata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tech.ada.bootcamp.cafe.entidades.Item;
import tech.ada.bootcamp.cafe.entidades.TipoUnidade;
import tech.ada.bootcamp.cafe.repository.ItemRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
public class ItemLoadData implements CommandLineRunner {

    private final ItemRepository itemRepository;
    private final ComboLoadData comboLoadData;
    private static final List<String> IDENTIFICADOR_ITEMS = Arrays.asList("5859e428-d8ed-4c24-a7db-1ebd7b70a3d0"
            , "aa1a323c-69c6-4f23-9f85-e8056650dea6"
            , "13530e8e-6aab-49f8-ba24-aea4ce68cfc2");

    @Override
    public void run(String... args) throws Exception {
        if (itemRepository.count() == 0) {
            List<Item> items = createItems();
            items.forEach(itemRepository::save);
            comboLoadData.run();
        }
    }

    private List<Item> createItems() {
        List<String> descricaoItems = Arrays.asList("Café torra clara", "Café torra média",
                "Café torra escura");

        Map<String, String> mapItems = IntStream.range(0, IDENTIFICADOR_ITEMS.size())
                .boxed()
                .collect(Collectors.toMap(IDENTIFICADOR_ITEMS::get, descricaoItems::get));
        List<Item> items = new ArrayList<>();
        mapItems.forEach((chave, descricao) -> {
            Item item = generateItem(chave, descricao);
            items.add(item);
        });
        return items;

    }

    private Item generateItem(String chave, String descricao) {
        Item item = new Item();
        item.setDescricao(descricao);
        item.setTipoUnidade(TipoUnidade.GRAMA);
        item.setQuantidadeUnidade(250L);
        item.setValorUnitario(20.0);
        item.setIdentificador(chave);
        log.info("Item Salvo {}", item);
        return item;
    }
}
