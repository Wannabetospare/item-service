package hello.itemservice.web.item.basic;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;



@Controller // 반환 값이 String 이면 뷰 이름으로 인식된다. 그래서 뷰를 찾고 뷰가 랜더링 된다. -> 메서드 반환값이 전부 String 인 이유 !!!
@RequestMapping("/basic/items") // 요청 정보를 매핑한다. 해당 URL이 호출되면 이 메서드가 호출된다. 애노테이션을 기반으로 동작함
@RequiredArgsConstructor // 생성자 자동생성, fianl 값을 자동주입해서 생성
public class BasicItemController {
    private final ItemRepository itemRepository; // 생성자의 주입 요소


    // 매핑 종류 - Get
    // 매핑 주소 - /basic/items
    // 이름 - items
    // 매개변수 - model
    // 동작 - itemRepository 에서 모든 아이템을 리스트형으로 뽑아서 모델에 싣는다.
    // 반환 - 주소값 반환
    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }


    // 매핑 종류 - Get
    // 매핑 주소 - /basic/items/{itemid}
    // 이름 - item
    // 매개변수 - itemId, model
    // 동작 - itemId 로 item 을 찾고, 찾은 item 을 모델에 싣는다.
    // 반환 - 주소값 반환
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    // 매핑 종류 - Get
    // 매핑 주소 - /basic/items/add
    // 이름 - addForm
    // 매개변수 - x
    // 동작 - x
    // 반환 - 주소값 반환
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    // @PostMapping("/add")
    // 매핑 종류 - Post
    // 매핑 주소 - /basic/items/add
    // 이름 - addItemV1
    // 매개변수 - itemName, price, quantity, model
    // 동작 - item 을 생성하고 set 으로 세팅해준 다음, itemRepository 에 저장하고, 모델에 싣는다
    // 반환 - 주소값 반환
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

    // @PostMapping("/add")
    // 같은 동작
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {

        itemRepository.save(item);

    //    model.addAttribute("item", item);  - @ModelAttribute 가 자동으로 추가해서 생략가능

        return "basic/item";
    }

    /**
     * @ModelAttribute name 생략 가능
     * model.addAttribute(item); 자동 추가, 생략 가능
     * 생략시 model에 저장되는 name은 클래스명 첫글자만 소문자로 등록 Item -> item
     */
    // @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    /**
     * @ModelAttribute 자체 생략 가능
     * model.addAttribute(item) 자동 추가
     */
    // @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }

    //  V5, V6 은 새로고침시 발생하는 오류를 잡기 위한 리다이렉트로 주소를 설정하는 방법이다.


    /**
     * PRG - Post/Redirect/Get
     */
    //@PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    /**
     * RedirectAttributes, 메세지 추가
     */
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }



    // 매핑 종류 - Get
    // 매핑 주소 - /basic/items/{itemId}/edit
    // 이름 - editForm
    // 매개변수 - itemId, model
    // 동작 - itemId 로 item 을 찾아서 같은 경로로 보내서 수정한다.
    // 반환 - 주소값 반환
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
        }

    // 매핑 종류 - Post
    // 매핑 주소 - /basic/items/{itemId}/edit
    // 이름 - edit
    // 매개변수 - itemId, item
    // 동작 - itemRepository 에서 item 을 찾아서 업데이트 한다.
    // 반환 - 주소값 반환
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }




    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct // 해당 빈의 의존관계가 모두 주입되고 나면 초기화 용도로 호출된다.
    public void init() {

        itemRepository.save(new Item("testA", 10000, 10));
        itemRepository.save(new Item("testB", 20000, 20));
    }
}