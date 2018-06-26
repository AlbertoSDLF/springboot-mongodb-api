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
public class CarRepositoryTests {

	// @Autowired
	// private CarRepository carRepository;

	@Autowired
	private MockMvc mockMvc;

	// @Before
	// public void deleteAllBeforeTests() throws Exception {
	// this.carRepository.deleteAll();
	// }

	@Test
	public void shouldCreateEntity() throws Exception {
		this.mockMvc.perform(post("/car").content("{\"make\": \"Lamborghini\", \"model\":\"Diablo\"}"))
				.andExpect(status().isCreated()).andExpect(header().string("Location", containsString("car/")));
	}

	@Test
	public void shouldDeleteEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/car").content("{ \"make\": \"Ferrari\", \"model\":\"F40\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		this.mockMvc.perform(delete(location)).andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isNotFound());
	}

	@Test
	public void shouldPartiallyUpdateEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/car").content("{\"make\": \"Pagani\", \"model\":\"Zonda\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		this.mockMvc.perform(patch(location).content("{\"model\": \"Huayra\"}")).andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.make").value("Pagani"))
				.andExpect(jsonPath("$.model").value("Huayra"));
	}

	@Test
	public void shouldQueryEntity() throws Exception {
		this.mockMvc.perform(post("/car").content("{ \"make\": \"Porsche\", \"model\":\"918 Spyder\"}"))
				.andExpect(status().isCreated());

		this.mockMvc.perform(get("/car/search/findByMake?make={make}", "Porsche")).andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.car[0].model").value("918 Spyder"));
	}

	@Test
	public void shouldRetrieveEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/car").content("{\"make\": \"Porsche\", \"model\":\"Cayman GT4\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");
		this.mockMvc.perform(get(location)).andExpect(status().isOk()).andExpect(jsonPath("$.make").value("Porsche"))
				.andExpect(jsonPath("$.model").value("Cayman GT4"));
	}

	@Test
	public void shouldReturnRepositoryIndex() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$._links.car").exists());
	}

	@Test
	public void shouldUpdateEntity() throws Exception {
		final MvcResult mvcResult = this.mockMvc
				.perform(post("/car").content("{\"make\": \"McLaren\", \"model\":\"Senna\"}"))
				.andExpect(status().isCreated()).andReturn();

		final String location = mvcResult.getResponse().getHeader("Location");

		this.mockMvc.perform(put(location).content("{\"make\": \"Mercedes-Benz\", \"model\":\"AMG GT\"}"))
				.andExpect(status().isNoContent());

		this.mockMvc.perform(get(location)).andExpect(status().isOk())
				.andExpect(jsonPath("$.make").value("Mercedes-Benz")).andExpect(jsonPath("$.model").value("AMG GT"));
	}
}