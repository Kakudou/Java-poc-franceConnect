package controllers;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import security.SecuredAction;
import security.openDataSoft.OpenDataSoft;
import security.openIdConnect.franceConnect.CheckToken;
import security.openIdConnect.franceConnect.FranceConnect;
import security.openIdConnect.franceConnect.IdentitePivot;
import views.html.index;
import views.html.home;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {
	
	public static Result index() {
		Result result = ok(index.render());
		
		if (session().get("X-ACCESS-TOKEN") != null){
			//TODO on réduire les request pour cause de mauvais wifi
			FranceConnect fc = FranceConnect.getInstance();
			CheckToken ct = fc.checkToken(session().get("X-ACCESS-TOKEN"));
			if (ct != null){
				result = redirect(routes.Application.home());
			}
		}
		return result;
	}
	
	@With(SecuredAction.class)
	public static Result home(){
		FranceConnect fc = FranceConnect.getInstance();
		String accessToken = session().get("X-ACCESS-TOKEN");
		IdentitePivot idp = fc.getIdentitePivot(accessToken);
//		IdentitePivot idp = new IdentitePivot("", "", "", "", "", "");
		return ok(home.render(idp));
	}

	public static Result login() {
		String scopeComaSeparate = "email,profile,openid";

		FranceConnect fc = FranceConnect.getInstance();
		return redirect(fc.getAuthUrl(scopeComaSeparate));
	}

	public static Result callback(String error, String state, String code) {
		Result result = redirect(routes.Application.home());

		if (error != null || "access_denied".equalsIgnoreCase(error)) {
			flash("error", "access_denied");
			result = badRequest();
		}
		if (state == null || session().get("state") == null
				|| !session().get("state").equals(state)) {
			flash("error", "forbidden");
			result = forbidden();
		}

		FranceConnect fc = FranceConnect.getInstance();
		fc.fullAuth(code);

		return result;
	}

	@With(SecuredAction.class)
	public static Result logout() {
		session().clear();
		FranceConnect fc = FranceConnect.getInstance();
		return redirect(fc.logout());
	}

}
