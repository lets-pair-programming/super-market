package mob.code.supermarket;

import com.alibaba.fastjson.JSON;
import mob.code.supermarket.domain.Item;
import mob.code.supermarket.domain.ItemRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SupermarketControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemFactory itemFactory;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        itemRepository.save(new Item("12345678", "pizza", "", 15.00, "0"));
        itemRepository.save(new Item("22345678", "milk", "L", 12.30, "1"));
    }

    @After
    public void tearDown() {
        itemFactory.clear();
    }

    @Test
    public void should_scan_success() throws Exception {
        String[] barcodes = {"    _  _     _  _  _  _ ",
                "|   _| _||_||_ |_   ||_|",
                "|  |_  _|  | _||_|  ||_|",
                "",
                "    _  _     _  _  _  _ ",
                "|   _| _||_||_ |_   ||_|",
                "|  |_  _|  | _||_|  ||_|",
                "",
                " _  _  _     _  _  _  _ ",
                " _| _| _||_||_ |_   ||_|",
                "|_ |_  _|  | _||_|  ||_|",
                "3.5"
        };
        String contentAsString = mockMvc.perform(post("/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(barcodes)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(contentAsString);
        JSONAssert.assertEquals(
                "{\"data\":[\"****** SuperMarket receipt ******\",\"pizza: 2 x 15.00 --- 30.00\",\"milk: 3.5(L) x 12.30 --- 43.05\",\"---------------------------------\",\"total: 73.05(CNY)\",\"*********************************\"]}",
                contentAsString,
                false);
    }

    @Test
    public void 如果传入的条码在数据库中不存在_应该返回错误信息() throws Exception {
        String[] barcodes = {
                " _  _  _  _  _  _  _  _ ",
                "|_||_||_||_||_||_||_||_|",
                "|_||_||_||_||_||_||_||_|",
                ""
        };
        String contentAsString = mockMvc.perform(post("/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(barcodes)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals(
                "{\"data\":null,\"error\":\"item doesn't exist: 88888888\"}",
                contentAsString,
                false);
    }
}
