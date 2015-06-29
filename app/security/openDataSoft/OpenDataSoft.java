package security.openDataSoft;

import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import security.openIdConnect.franceConnect.CheckToken;
import security.openIdConnect.franceConnect.FranceConnect;
import security.openIdConnect.franceConnect.IdentitePivot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OpenDataSoft
{
  public static ObjectNode checkSituation(CheckToken token)
  {
    ObjectNode node = Json.newObject();
    
	FranceConnect fc = FranceConnect.getInstance();

	IdentitePivot idPivot = fc.getIdentitePivot(token.getAccess_token());
	StringBuilder query = new StringBuilder();

	query.append("nom_de_naissance:" + idPivot.getFamilyName());
	query.append(" AND ");
	query.append("lieu_de_naissance:" + idPivot.getBirthplace());
	query.append(" AND ");
	query.append("pays_de_naissance:" + idPivot.getBirthcountry());
	query.append(" AND ");
	query.append("sexe:" + idPivot.getGender());
//	query.append(" AND ");
	//TODO format date
//	query.append("date_de_naissance:" + idPivot.getBirthdate());
	query.append(" AND ");
	query.append("prenoms:" + idPivot.getGivenName());
    
    WSRequestHolder ws = WS.url("https://datafranceconnect.opendatasoft.com/api/records/1.0/search");
    ws.setQueryParameter("dataset", "situation_service_national");
    ws.setQueryParameter("q", query.toString());
    ws.setQueryParameter("access_token", token.getAccess_token());
    ws.setQueryParameter("pretty_print", "true");

    JsonNode responseJson = ws.get().get(5000).asJson();
    
    if (1 !=responseJson.get("nhits").asInt()) {
      if (0 == responseJson.get("nhits").asInt() ||  null == responseJson.get("nhits")) {
        node.put("error", "No results");
      }else{
    	  node.put("error", "Too many results");
      }
      } else {
      int situationSN = responseJson.get("records").get(0).get("fields").get("situation_sn").asInt();
      node.put("situation_sn", (situationSN == 1)?true:false);
      node.put("situation_clair", responseJson.get("records").get(0).get("fields").get("clair_situation").asText());
    }
    
    return node;
  }
  
  public static JsonNode checkSituations(CheckToken token, String query)
  {
    
    WSRequestHolder ws = WS.url("https://datafranceconnect.opendatasoft.com/api/records/1.0/search");
    ws.setQueryParameter("dataset", "situation_service_national");
    ws.setQueryParameter("q", query);
    ws.setQueryParameter("access_token", token.getAccess_token());
    ws.setQueryParameter("pretty_print", "true");

    JsonNode responseJson = ws.get().get(5000).asJson();
    
    return responseJson;
  }
}
