package fr.weytensjohann.springcefim;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class SpringCefimApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(SpringCefimApplicationTests.class);
	@Autowired
	private EntityManager entityManager;
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
	@Test
	void testDatabase(){
		 Query query = entityManager.createNativeQuery("show tables;");
		List<String> results = ((List<String>) query.getResultList());
		String resultList = String.join(" - ", results);
		logger.info("Connexion à la BDD :: SUCCESS");
		logger.info("Table list of databases = [{}]", resultList);
	}
}
