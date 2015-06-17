package security.openIdConnect.franceConnect.json;

import java.io.IOException;

import security.openIdConnect.franceConnect.IdentitePivot;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class IdentitePivotJsonDeserializer extends JsonDeserializer<IdentitePivot>
{

  @Override
  public IdentitePivot deserialize(JsonParser jp, DeserializationContext arg1) throws IOException, JsonProcessingException
  {
   JsonNode node = jp.getCodec().readTree(jp);
    
    String givenName = node.get("given_name").asText();
    String familyName = node.get("family_name").asText();
    String birthdate = node.get("birthdate").asText();
    String gender = node.get("gender").asText();
    String birthplace = node.get("birthplace").asText();
    String birthcountry = node.get("birthcountry").asText();
    
    return new IdentitePivot(givenName, familyName, birthdate, gender, birthplace, birthcountry);
  }
  
}
