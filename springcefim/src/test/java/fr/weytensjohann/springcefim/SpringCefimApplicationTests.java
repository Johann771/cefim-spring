package fr.weytensjohann.springcefim;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SpringCefimApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
	}
	@Test
	public void testHelloWorld() throws Exception{
			ResultMatcher testStatus = status().isOk();
			RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/hello/world").contentType(MediaType.TEXT_PLAIN);
			mvc.perform(requestBuilder)
					.andExpect(testStatus)
					.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
					.andExpect(content().string("Hello World"));
	}
}
