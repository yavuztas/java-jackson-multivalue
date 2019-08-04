package dev.yavuztas.samples.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import dev.yavuztas.samples.jackson.model.CommentModel;

/**
 * Custom JsonDeserializer for Jackson to handle list of {@link CommentModel}
 * deserialization
 * 
 * @author Yavuz Tas
 *
 */
public class CommentListDeserializer extends StdDeserializer<List> {

    private static final TypeReference<List<CommentModel>> LIST_OF_COMMENTS_TYPE = new TypeReference<List<CommentModel>>() {
    };

    public CommentListDeserializer() {
        this(null);
    }

    private CommentListDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public List deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        List<CommentModel> commentList = new ArrayList<>();

        JsonToken token = p.currentToken();

        if (JsonToken.START_ARRAY.equals(token)) {
            List<CommentModel> listOfArticles = p.readValueAs(LIST_OF_COMMENTS_TYPE);
            commentList.addAll(listOfArticles);
        }

        if (JsonToken.START_OBJECT.equals(token)) {
            CommentModel article = p.readValueAs(CommentModel.class);
            commentList.add(article);
        }

        return commentList;
    }

}
