package dev.yavuztas.samples.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import dev.yavuztas.samples.jackson.model.CommentModel;

/**
 * Custom JsonDeserializer for Jackson to handle list of {@link CommentModel}
 * deserialization
 * 
 * @author Yavuz Tas
 *
 */
public class SingleAwareListDeserializer extends StdDeserializer<List> implements ContextualDeserializer {

	private Class<?> contentClassType;

	public SingleAwareListDeserializer() {
		this(null);
	}

	private SingleAwareListDeserializer(JavaType valueType) {
		super(valueType);
	}

	@Override
	public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
		// we use ContextualDeserializer to obtain content class type
		contentClassType = property.getType().getContentType().getRawClass();
		return this;
	}

	@Override
	public List deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

		List list = new ArrayList<>();

		JsonToken token = p.currentToken();
		// if token is array type then perform object deserialization to each element
		if (JsonToken.START_ARRAY.equals(token)) {
			while (p.nextToken() != null) {
				if (JsonToken.START_OBJECT.equals(p.currentToken())) {
					list.add(deserializeObject(p));
				}
			}
		}

		// if token is object type
		if (JsonToken.START_OBJECT.equals(token)) {
			list.add(deserializeObject(p));
		}

		return list;
	}

	private Object deserializeObject(JsonParser p) throws IOException {
		// just use jackson default object deserializer by using content type
		return p.readValueAs(contentClassType);
	}

}
