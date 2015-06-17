package security.openDataSoft;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import security.openIdConnect.franceConnect.CheckToken;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpenDataSoft
{
  public static ObjectNode askDataFranceConnect(CheckToken token, String query)
  {
    
    ObjectNode node = Json.newObject();
    
    WSRequestHolder ws = WS.url("https://datafranceconnect.opendatasoft.com/api/records/1.0/search");
    ws.setQueryParameter("dataset", "dgfip_rp");
    ws.setQueryParameter("q", query);
    ws.setQueryParameter("access_token", token.getAccess_token());
    ws.setQueryParameter("pretty_print", "true");

    System.out.println(ws.getUrl()+ws.getQueryParameters());
    JsonNode responseJson = ws.get().get(5000).asJson();
    System.out.println(responseJson);
    
    if (!"1".equals(responseJson.get("nhits"))) {
      if ("0".equals(responseJson.get("nhits")) ||  null == responseJson.get("nhits")) {
        node.put("error", "No results");
      }
      node.put("error", "Too many results");
    } else {
      node.put("result", responseJson.get("records").get(0).get("fields").get("result").asText());
    }
    
    return node;
  }
}
