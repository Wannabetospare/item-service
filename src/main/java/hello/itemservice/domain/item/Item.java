package hello.itemservice.domain.item;


import lombok.Data;

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;



    // 아이템 기본 생성자
    public Item() {
    }


    // 아이템 id를 제외한 생성자 -> id는 저장하면서 repository 에서 넣어 줄 예정
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

