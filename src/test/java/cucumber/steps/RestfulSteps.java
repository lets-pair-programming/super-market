package cucumber.steps;

import cucumber.util.RestfulHelper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.cucumber.spring.CucumberContextConfiguration;
import mob.code.supermarket.Application;
import mob.code.supermarket.ItemFactory;
import mob.code.supermarket.domain.Item;
import mob.code.supermarket.domain.ItemRepository;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;


@ContextConfiguration(classes = Application.class, loader = SpringBootContextLoader.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class RestfulSteps {
    @LocalServerPort
    private int port;

    private ResponseEntity<String> response;
    private List<String> scanData;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemFactory itemFactory;

    @When("I {string} the api {string}")
    public void iCallTheApi(String method, String apiName) {
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        response = RestfulHelper.connect(port).require(httpMethod, apiName);
    }

    @When("I {string} the api {string} with")
    public void iCallTheApi(String method, String apiName, String body) {
        HttpMethod httpMethod = HttpMethod.valueOf(method.toUpperCase());
        response = RestfulHelper.connect(port).require(httpMethod, apiName, body);
    }

    @Then("the server response will match {string}")
    public void theServerResponseWillMatchInline(String expectedResponse) throws JSONException {
        verifyResponseMatch(expectedResponse);
    }

    @Then("the server response will match$")
    public void theServerResponseWillMatch(String expectedResponse) throws JSONException {
        verifyResponseMatch(expectedResponse);
    }

    private void verifyResponseMatch(String expectedResponse) throws JSONException {
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @When("??????????????????????????????")
    public void ??????????????????????????????() {

    }

    @Given("?????????????????????")
    public void ?????????????????????(List<Map<String, String>> items) {
        this.scanData = items.stream().map(item -> item.get("??????")).collect(Collectors.toList());
    }

    @And("?????????")
    public void ?????????(List<Map<String, String>> items) {
        itemFactory.clear();
        items.forEach(item ->
                itemRepository.save(new Item(item.get("??????"), item.get("??????"), StringUtils.isEmpty(item.get("??????")) ? "" : item.get("??????"), Double.valueOf(item.get("??????")), item.get("??????")))
        );
    }

    @Then("?????????????????????")
    public void ?????????????????????(String expected) throws JSONException {
        HttpMethod httpMethod = HttpMethod.valueOf("POST");
        response = RestfulHelper.connect(port).require(httpMethod, "/scan", new Gson().toJson(scanData));
        System.out.println("expected = " + expected + ", \nresponse data = " + response.getBody());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }
}
