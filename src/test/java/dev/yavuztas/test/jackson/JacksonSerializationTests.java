package dev.yavuztas.test.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.yavuztas.samples.jackson.model.ArticleModel;
import dev.yavuztas.samples.jackson.model.CommentModel;
import junit.framework.Assert;

/**
 * Unit tests for serialization operations with Gson
 * 
 * @author Yavuz Tas
 *
 */
public class JacksonSerializationTests {

    private ObjectMapper mapper;
    private ArticleModel mockModelSingleValue;
    private ArticleModel mockModelMultiValue;

    @Before
    public void setup() {

        // initialize gson instance
        this.mapper = new ObjectMapper();

        // prepare the mock data
        this.mockModelSingleValue = new ArticleModel();
        this.mockModelSingleValue.setId(1L);
        this.mockModelSingleValue.setName("sample article");

        List<CommentModel> comments = new ArrayList<>();

        CommentModel comment1 = new CommentModel();
        comment1.setId(1L);
        comment1.setText("some comment text");

        comments.add(comment1);

        this.mockModelSingleValue.setComments(comments);

        this.mockModelMultiValue = new ArticleModel();
        this.mockModelMultiValue.setId(1L);
        this.mockModelMultiValue.setName("sample article");

        comments = new ArrayList<>();

        comment1 = new CommentModel();
        comment1.setId(1L);
        comment1.setText("some comment text");

        CommentModel comment2 = new CommentModel();
        comment2.setId(2L);
        comment2.setText("some another comment text");

        comments.add(comment1);
        comments.add(comment2);

        this.mockModelMultiValue.setComments(comments);

    }

    @Test
    public void serializeFromMultipleValueTest() throws IOException {

        String json = mapper.writeValueAsString(this.mockModelMultiValue);

        ArticleModel model = mapper.readValue(json, ArticleModel.class);

        Assert.assertEquals(ArticleModel.class, model.getClass());
        Assert.assertEquals(model.getComments()
            .get(0)
            .getClass(), CommentModel.class);

        Assert.assertEquals(1, model.getId()
            .longValue());
        Assert.assertEquals("sample article", model.getName());

        Assert.assertEquals(2, model.getComments()
            .size());
        Assert.assertEquals(1, model.getComments()
            .get(0)
            .getId()
            .longValue());
        Assert.assertEquals("some comment text", model.getComments()
            .get(0)
            .getText());
        Assert.assertEquals(2, model.getComments()
            .get(1)
            .getId()
            .longValue());
        Assert.assertEquals("some another comment text", model.getComments()
            .get(1)
            .getText());

    }

    @Test
    public void serializeFromSingleValueTest() throws IOException {

        String json = mapper.writeValueAsString(this.mockModelSingleValue);

        ArticleModel model = mapper.readValue(json, ArticleModel.class);

        Assert.assertEquals(ArticleModel.class, model.getClass());
        Assert.assertEquals(model.getComments()
            .get(0)
            .getClass(), CommentModel.class);

        Assert.assertEquals(1, model.getId()
            .longValue());
        Assert.assertEquals("sample article", model.getName());

        Assert.assertEquals(1, model.getComments()
            .size());
        Assert.assertEquals(1, model.getComments()
            .get(0)
            .getId()
            .longValue());
        Assert.assertEquals("some comment text", model.getComments()
            .get(0)
            .getText());

    }

}
