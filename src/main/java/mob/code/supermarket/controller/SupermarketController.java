package mob.code.supermarket.controller;

import mob.code.supermarket.domain.Item;
import mob.code.supermarket.dto.Response;
import mob.code.supermarket.domain.RawBarcodeParser;
import mob.code.supermarket.domain.SupermarketException;
import mob.code.supermarket.domain.ItemRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class SupermarketController {
    public final ItemRepository itemRepository;
    private final RawBarcodeParser rawBarcodeParser = new RawBarcodeParser();

    public SupermarketController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("ping")
    public Response<String> ping() {
        return Response.of("pong");
    }

    @GetMapping("dontcall")
    public Response<String> responseError() {
        throw new SupermarketException("It is a sample error");
    }

    @GetMapping("item")
    public Response<List<Item>> getItems() {
        return Response.of(itemRepository.findAll());
    }

    @PostMapping("tryBarCode")
    public List<String> tryBarCode(@RequestBody String[] barcodes) {
        return rawBarcodeParser.parse(barcodes);
    }

    @PostMapping("printBill")
    public List<String> printBill(@RequestBody String[] barcodes) {
        return rawBarcodeParser.print(barcodes);
    }
}
