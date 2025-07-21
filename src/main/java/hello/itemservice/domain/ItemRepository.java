package hello.itemservice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    /** 아래 둘: 멀티 스레드 환경에서 동시 접근 시 문제 발생 가능
     * 멀티 스레드 환경에서는 ConcurrentHashmap / Atomiclong 사용 **/
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L; // static -> 유일해야 하는 것들

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll(){
        return new ArrayList<>(store.values());
        // ArrayList로 감싸서 반환 -> 반환된 데이터가 수정되어도 store 원본에 영향 가지 않음
    }

    public void update(Long itemId, Item updateParam){
        /** updateParam을 Item으로 놔 두면 id까지 접근 가능하게 되므로
         *  현업에서는 update에 사용하는 파라미터인 Itemname, Price, Quantity만 포함된 새 객체(이름 예시: ItemParamDto) 만드는 게 좋음
         */
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore(){
        store.clear();
    }
}
