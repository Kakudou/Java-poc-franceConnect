package security.openIdConnect.franceConnect.json;

import java.io.IOException;

import security.openIdConnect.franceConnect.CheckToken;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CheckTokenJsonDeserializer extends JsonDeserializer<CheckToken>
{
  
  @Override
  public CheckToken deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException
  {
    JsonNode node = jp.getCodec().readTree(jp);
    
    String identity = node.get("identity").asText();
    String scope = node.get("scope").asText();
    String client = node.get("client").asText();
    String identity_provider_host = node.get("identity_provider_host").asText();
    String identity_provider_id = node.get("identity_provider_id").asText();
    String acr = node.get("acr").asText();
    
    return new CheckToken(identity, scope, client, identity_provider_host, identity_provider_id, acr);
  }
  
}
