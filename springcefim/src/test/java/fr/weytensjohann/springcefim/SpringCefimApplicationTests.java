package fr.weytensjohann.springcefim;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.weytensjohann.springcefim.feature.database.DatabaseService;
import fr.weytensjohann.springcefim.feature.product.Produit;
import fr.weytensjohann.springcefim.feature.product.ProduitDto;
import fr.weytensjohann.springcefim.feature.product.ProduitWithPriceDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class SpringCefimApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(SpringCefimApplicationTests.class);
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private DatabaseService databaseService;
	@Autowired
	private ObjectMapper objectMapper;

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
	@Test
	void testProductList(){
		ProduitDto p1 = new ProduitDto(2, "Smartphone Samsung", "Smartphone Samsung Galaxy S21 5G - 128 Go - Phantom Gray");
		ProduitDto p2 = new ProduitDto(3, "Ordinateur portable", "Ordinateur portable HP Spectre x360 14-ea0132nf");

		List<ProduitDto> listProduits = databaseService.getListProduct();

		assert listProduits.containsAll(Arrays.asList(p1, p2));
	}
	@Test
	void TestgetProductNameList (){
		List<String> results = this.databaseService.getProductNameList();
		String resultList = String.join(" - ", results);

		logger.info("result : "+ resultList);
	}
	boolean testEquality(Produit produitEntity, ProduitWithPriceDto produitWithPriceDto){
		return Objects.equals(produitEntity.getName(), produitWithPriceDto.getNom()) && Objects.equals(produitEntity.getDescription(), produitWithPriceDto.getDescription())
				&& Objects.equals(BigDecimal.valueOf(produitEntity.getUnitPrice()), produitWithPriceDto.getUnitPrice());
	}
	@Test
	void testProduitFromEntity(){
		// Création de nos tests
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(2, "Smartphone Samsung", "Smartphone Samsung Galaxy S21 5G - 128 Go - Phantom Gray", BigDecimal.valueOf(999.0));
		ProduitWithPriceDto p2 = new ProduitWithPriceDto(3, "Ordinateur portable", "Ordinateur portable HP Spectre x360 14-ea0132nf", BigDecimal.valueOf(1499.0));

		// Récupération de ma liste de produit via mes entity
		List<Produit> listProductFromEntity = databaseService.getListProductFromEntity();

		// Test
		assert listProductFromEntity.stream().filter(produit -> produit.getProductId()<=3).allMatch(produit -> testEquality(produit, p1) || testEquality(produit, p2));
	}
	@Test
	void testGetSamsung(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(2, "Smartphone Samsung", "Smartphone Samsung Galaxy S21 5G - 128 Go - Phantom Gray", BigDecimal.valueOf(999.0));
		ProduitWithPriceDto oneProduct = databaseService.getOneProduct((int) p1.getId());
		assert oneProduct.equals(p1);
	}
	@Test
	void testGetSamsungFromEntity(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(2, "Smartphone Samsung", "Smartphone Samsung Galaxy S21 5G - 128 Go - Phantom Gray", BigDecimal.valueOf(999.0));
		Produit oneProduct = databaseService.getOneProductEntity(p1.getId());
		assert testEquality(oneProduct, p1);
	}
	@Test
	void testGetIphoneByAvis(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(7, "iphone", "portable", BigDecimal.valueOf(1000.0));
		ProduitWithPriceDto p2 = new ProduitWithPriceDto(8, "iphone 13", "portable", BigDecimal.valueOf(1200.0));
		List<ProduitWithPriceDto> listProduits = databaseService.getProductByAvis(10);
		assert listProduits.stream().allMatch(produit -> produit.equals(p1) || produit.equals(p2));
	}
	//A revoir
	@Test
	void testGetIphoneByName(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(7, "iphone", "portable", BigDecimal.valueOf(1000.0));
		ProduitWithPriceDto p2 = new ProduitWithPriceDto(8, "iphone 13", "portable", BigDecimal.valueOf(1200.0));
		List<ProduitWithPriceDto> listProduits = databaseService.getProductByName("iphone");
		assert listProduits.stream().allMatch(produit -> produit.equals(p1) || produit.equals(p2));
	}

	@Test
	void testGetIphoneEntitiesByName(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(7, "iphone", "portable", BigDecimal.valueOf(1000.0));
		ProduitWithPriceDto p2 = new ProduitWithPriceDto(8, "iphone 13", "portable", BigDecimal.valueOf(1200.0));

		List<Produit> listProduits = databaseService.getProductEntityByName("iphone");

		assert listProduits.stream().allMatch(produit -> testEquality(produit, p1) || testEquality(produit, p2));
	}
	@Test
	void testGetIphoneEntitiesByAvisNote(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(7, "iphone", "portable", BigDecimal.valueOf(1000.0));
		ProduitWithPriceDto p2 = new ProduitWithPriceDto(8, "iphone 13", "portable", BigDecimal.valueOf(1200.0));

		List<Produit> listProduits = databaseService.getProductEntityByAvisNote();
		logger.info("listProduits : "+ listProduits.toString());
		assert listProduits.stream().allMatch(produit -> testEquality(produit, p1) || testEquality(produit, p2));
	}
	@Test
	void testInsertProduit(){
		ProduitWithPriceDto p1 = new ProduitWithPriceDto(null, "iphone 14", "portable", BigDecimal.valueOf(1200.0));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"iphone 14\",\"description\":\"portable\",\"unitPrice\":1200.0}");
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
		//JsonPathResultMatchers resultNameProduct = MockMvcResultMatchers.jsonPath("$.name", "table");
		try{
			mvc.perform(requestBuilder)
					.andExpect(resultStatus);
			//.andExpect(resultNameProduct);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	@Test
	void testDeleteProduct() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/product/delete/3");
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

		mvc.perform(requestBuilder)
				.andExpect(resultStatus);
	}
	@Test
	void testDeleteNotFound() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/product/delete/20");
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().isNotFound();
		mvc.perform(requestBuilder).andExpect(resultMatcher);

	}
	//A revoir
	@Test
	void testDeleteProductByName() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/product?productName=iphone");
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();

		mvc.perform(requestBuilder)
				.andExpect(resultStatus);
	}
	@Test
	void getProductById() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/product/get/10");
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
		MvcResult result = mvc.perform(requestBuilder)
				.andExpect(resultStatus)
				.andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println("Response content: " + content);
	}

	@Test
	void UpdateProductById() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/product/update/9")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"update\",\"description\":\"works\",\"unitPrice\":1200.0}");
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isOk();
		mvc.perform(requestBuilder)
				.andExpect(resultStatus);
	}
	@Test
	void testUpdateProductWithExistingName() throws Exception{
		Map<String, String> listFields = new HashMap<>(){{
			put("description", "Pantalon de jogging confortable avec poches zippées");
			put("name", "Pantalon de jogging");
		}};

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.put("/api/product/update/2")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(listFields));
		ResultMatcher resultStatus = MockMvcResultMatchers.status().isConflict();

		mvc.perform(requestBuilder)
				.andExpect(resultStatus);
	}
	@Test
	void testGetProductNotFound() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/product/get/20");
		ResultMatcher resultMatcher = MockMvcResultMatchers.status().isNotFound();
		mvc.perform(requestBuilder).andExpect(resultMatcher);
	}
}
