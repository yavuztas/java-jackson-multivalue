package dev.yavuztas.test.jackson;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.yavuztas.samples.jackson.model.ArticleModel;
import dev.yavuztas.samples.jackson.model.ArticleModelStatic;
import dev.yavuztas.samples.jackson.model.CommentModel;
import junit.framework.Assert;

/**
 * Unit tests for deserialization operations with Gson
 * 
 * @author Yavuz Tas
 *
 */
public class JacksonDeserializationTests {

	private ObjectMapper mapper;
	private String mockResponseSingleValue;
	private String mockResponseMultiValue;

	@Before
	public void setup() {

		// initialize gson instance
		this.mapper = new ObjectMapper();
		// mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

		// prepare the mock data
		ObjectNode response = mapper.createObjectNode();

		response.put("id", 1);
		response.put("name", "sample article");

		ObjectNode comment = mapper.createObjectNode();
		comment.put("id", 1);
		comment.put("text", "some comment text");

		response.set("comments", comment);

		this.mockResponseSingleValue = response.toString();

		response = mapper.createObjectNode();
		response.put("id", 1);
		response.put("name", "sample article");

		ObjectNode comment1 = mapper.createObjectNode();
		comment1.put("id", 1);
		comment1.put("text", "some comment text");

		ObjectNode comment2 = mapper.createObjectNode();
		comment2.put("id", 2);
		comment2.put("text", "some another comment text");

		ArrayNode array = mapper.createArrayNode();
		array.add(comment1);
		array.add(comment2);

		response.set("comments", array);

		this.mockResponseMultiValue = response.toString();

	}

	@Test
	public void deserializeStaticMultipleValueTest() throws JsonParseException, JsonMappingException, IOException {

		ArticleModelStatic model = mapper.readValue(this.mockResponseMultiValue, ArticleModelStatic.class);

		Assert.assertEquals(ArticleModelStatic.class, model.getClass());
		Assert.assertEquals(model.getComments().get(0).getClass(), CommentModel.class);

		Assert.assertEquals(1, model.getId().longValue());
		Assert.assertEquals("sample article", model.getName());

		Assert.assertEquals(2, model.getComments().size());
		Assert.assertEquals(1, model.getComments().get(0).getId().longValue());
		Assert.assertEquals("some comment text", model.getComments().get(0).getText());
		Assert.assertEquals(2, model.getComments().get(1).getId().longValue());
		Assert.assertEquals("some another comment text", model.getComments().get(1).getText());

	}

	@Test
	public void deserializeStaticSingleValueTest() throws JsonParseException, JsonMappingException, IOException {

		ArticleModelStatic model = mapper.readValue(this.mockResponseSingleValue, ArticleModelStatic.class);

		Assert.assertEquals(ArticleModelStatic.class, model.getClass());
		Assert.assertEquals(model.getComments().get(0).getClass(), CommentModel.class);

		Assert.assertEquals(1, model.getId().longValue());
		Assert.assertEquals("sample article", model.getName());

		Assert.assertEquals(1, model.getComments().size());
		Assert.assertEquals(1, model.getComments().get(0).getId().longValue());
		Assert.assertEquals("some comment text", model.getComments().get(0).getText());

	}

	@Test
	public void deserializeGenericMultipleValueTest() throws JsonParseException, JsonMappingException, IOException {

		ArticleModel model = mapper.readValue(this.mockResponseMultiValue, ArticleModel.class);

		Assert.assertEquals(ArticleModel.class, model.getClass());
		Assert.assertEquals(model.getComments().get(0).getClass(), CommentModel.class);

		Assert.assertEquals(1, model.getId().longValue());
		Assert.assertEquals("sample article", model.getName());

		Assert.assertEquals(2, model.getComments().size());
		Assert.assertEquals(1, model.getComments().get(0).getId().longValue());
		Assert.assertEquals("some comment text", model.getComments().get(0).getText());
		Assert.assertEquals(2, model.getComments().get(1).getId().longValue());
		Assert.assertEquals("some another comment text", model.getComments().get(1).getText());

	}

	@Test
	public void deserializeGenericSingleValueTest() throws JsonParseException, JsonMappingException, IOException {

		ArticleModel model = mapper.readValue(this.mockResponseSingleValue, ArticleModel.class);

		Assert.assertEquals(ArticleModel.class, model.getClass());
		Assert.assertEquals(model.getComments().get(0).getClass(), CommentModel.class);

		Assert.assertEquals(1, model.getId().longValue());
		Assert.assertEquals("sample article", model.getName());

		Assert.assertEquals(1, model.getComments().size());
		Assert.assertEquals(1, model.getComments().get(0).getId().longValue());
		Assert.assertEquals("some comment text", model.getComments().get(0).getText());

	}

}
