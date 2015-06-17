package security.openIdConnect.franceConnect.json;

import java.io.IOException;

import security.openIdConnect.franceConnect.FranceConnectProvider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class FranceConnectProviderJsonDeserializer  extends JsonDeserializer<FranceConnectProvider>
{

  @Override
  public FranceConnectProvider deserialize(JsonParser jp, DeserializationContext arg1) throws IOException,
      JsonProcessingException
  {
    JsonNode node = jp.getCodec().readTree(jp);
    
    String issuer = node.get("issuer").asText();
    String clientId = node.get("client_id").asText();
    String clientSecret = node.get("client_secret").asText();
    String callback = node.get("callback").asText();
    String authorizationEndpoint = node.get("authorization_endpoint").asText();
    String tokenEndpoint = node.get("token_endpoint").asText();
    String userinfoEndpoint = node.get("userinfo_endpoint").asText();
    String logoutEndpoint = node.get("logout_endpoint").asText();
    String checktokenEndpoint = node.get("checktoken_endpoint").asText();
    String responseType = node.get("response_type").asText();
    
    return new FranceConnectProvider(issuer, clientId, clientSecret, callback, authorizationEndpoint, tokenEndpoint, userinfoEndpoint, logoutEndpoint, checktokenEndpoint, responseType);
  } 
}
