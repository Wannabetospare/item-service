package hello.itemservice.domain.item;


import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository // 저장소
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); // static 으로 선언 - 공유
    private static long sequence = 0L; // static 으로 선언 - 공유



    // 이름 - save
    // 매개변수 - item
    // 동작 - sequence 값을 생성하여 메서드가 호출될 때 마다 추가하고, store 에 id 와 item을 넣는다.
    // 반환값 - item
    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    // id 로 item 을 찾고 찾은 item 을 반환
    public Item findById(Long id) {
        return store.get(id);
    }


    // 모든 store 의 value 값을 List 형으로 반환
    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }


    // 이름 - update
    // 매개변수 - itemId, updateParam
    // 동작 - id 값으로 item을 찾고, 이름, 가격, 재고를 세팅한다.
    // 반환값 - x
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    // store 를 비운다.
    public void clearStore() {
        store.clear();
    }
}



