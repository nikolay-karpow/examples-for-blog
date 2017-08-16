package hello;


import com.google.common.io.ByteStreams;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import hello.dto.Greeting;
import hello.dto.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.google.common.io.ByteStreams.toByteArray;
import static com.google.common.io.Resources.getResource;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebMvc
public class GreetingsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(wac).build()
        );
    }

    @Test
    public void greetingReturnsCorrectDto() {
        Greeting greeting = RestAssuredMockMvc.given()
                .get("greeting")
                .andReturn()
                .as(Greeting.class);

        Greeting expected = new Greeting(new Person("Heisenberg"), "Guten Tag");
        assertEquals(greeting, expected);
    }

    @Test
    public void greetingsReturnsJsonWhichMatchesExpectedSchema() throws Exception {
        String greetingSchema = readGreetingSchema();

        RestAssuredMockMvc.given()
                .get("greeting")
                .then()
                .body(matchesJsonSchema(greetingSchema));
    }

    private String readGreetingSchema() throws IOException {
        return new String(toByteArray(getResource("greeting-schema.json").openStream()));
    }
}