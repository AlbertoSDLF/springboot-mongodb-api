package com.asitc.mongodbapi;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(MongoAutoConfiguration.class)
public class PersonRepositoryTests {

	// @Autowired
	// private CarRepository personRepository;

	@Autowired
	private MockMvc mockMvc;

	// @Before
	// public void deleteAllBeforeTests() throws Exception {
	// this.personRepository.deleteAll();
	// }

	@Test
	public void shouldCreateEntity() throws Exception {
		this.mockMvc.perform(post("/person").content("{\"first_name\": \"Ayrton\", \"last_name\":\"Senna\"}"))
				.andExpect(status().isCreated()).andExpect(header().string("Location", containsString("person/")));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/person").content("{ \"first_name\": \"Alain\", \"last_name\":\"Prost\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		this.mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/person").content("{\"first_name\": \"Nelson\", \"last_name\":\"Piquet\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		this.mockMvc.perform(patch(location).content("{\"first_name\": \"Nelsinho\"}"))
				.andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.first_name").value("Nelsinho"))
				.andExpect(jsonPath("$.last_name").value("Piquet"));
	}

	@Test
	public void shouldQueryEntity() throws Exception {
		this.mockMvc.perform(post("/person").content("{ \"first_name\": \"Lewis\", \"last_name\":\"Hamilton\"}"))
				.andExpect(status().isCreated());

		this.mockMvc.perform(get("/person/search/findByFirstName?first_name={firstName}", "Lewis"))
				.andExpect(status().isOk()).andExpect(jsonPath("$._embedded.person[0].first_name").value("Lewis"));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/person").content("{\"first_name\": \"Michael\", \"last_name\":\"Schumacher\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		this.mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.first_name").value("Michael"))
				.andExpect(jsonPath("$.last_name").value("Schumacher"));
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$._links.person").exists());
	}

	@Test
	public void shouldUpdateEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/person").content("{\"first_name\": \"Fernando\", \"last_name\":\"Alonso\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		this.mockMvc.perform(put(location).content("{\"first_name\": \"Frenando\", \"last_name\":\"Afondo\"}"))
				.andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.first_name").value("Frenando"))
				.andExpect(jsonPath("$.last_name").value("Afondo"));
	}
}