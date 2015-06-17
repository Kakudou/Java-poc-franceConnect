package security.openIdConnect.franceConnect.json;

import java.io.IOException;

import security.openIdConnect.franceConnect.Token;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class TokenJsonDeserializer extends JsonDeserializer<Token>
{

  @Override
  public Token deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException
  {
   JsonNode node = jp.getCodec().readTree(jp);

   	String accessToken = node.get("access_token").asText();
    String tokenType = node.get("token_type").asText();
    String expiresIn = node.get("expires_in").asText();
    String idToken = node.get("id_token").asText();

    return new Token(accessToken, tokenType, expiresIn, idToken);
  }
  
}
