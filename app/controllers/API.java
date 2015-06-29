package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.openDataSoft.OpenDataSoft;
import security.openIdConnect.franceConnect.CheckToken;
import security.openIdConnect.franceConnect.FranceConnect;
import security.openIdConnect.franceConnect.IdentitePivot;
import views.html.apidoc;

public class API extends Controller {
	
	public static Result doc(){
		return ok(apidoc.render());
	}
	
	public static Result checkSituation(String token) {
		Result result = badRequest();

		FranceConnect fc = FranceConnect.getInstance();
		CheckToken ct = fc.checkToken(token);
		
		if (ct != null) {
			result = ok(OpenDataSoft.checkSituation(ct));
		} else {
			ObjectNode node = Json.newObject();
			node.put("error", "Need valid accessToken as 'TOKEN' parameter");
			result = ok(node);
		}

		return result;
	}
	
	public static Result checkSituations(String token, String query) {
		Result result = badRequest();

		FranceConnect fc = FranceConnect.getInstance();
		CheckToken ct = fc.checkToken(token);

		if (ct != null) {
			result = ok(OpenDataSoft.checkSituations(ct, query));
		} else {
			ObjectNode node = Json.newObject();
			node.put("error", "Need valid accessToken as 'TOKEN' parameter And a fill form");
			result = ok(node);
		}
		return result;
	}

}
