package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import security.openDataSoft.OpenDataSoft;
import security.openIdConnect.franceConnect.CheckToken;
import security.openIdConnect.franceConnect.FranceConnect;
import security.openIdConnect.franceConnect.IdentitePivot;
import views.html.index;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

	public static Result index() {
		Result result = ok(index.render(""));
		if (session().get("X-ACCESS-TOKEN") != null) {
			FranceConnect fc = FranceConnect.getInstance();
			IdentitePivot idp = fc.getIdentitePivot(session().get(
					"X-ACCESS-TOKEN"));
			try {
				String serialized = new ObjectMapper()
						.writerWithDefaultPrettyPrinter().writeValueAsString(
								idp);
				result = ok(index.render(serialized));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Result login() {
		String scopeComaSeparate = "email,profile,openid";

		FranceConnect fc = FranceConnect.getInstance();
		return redirect(fc.getAuthUrl(scopeComaSeparate));
	}

	public static Result callback(String error, String state, String code) {
		Result result = badRequest();

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

		result = redirect(routes.Application.index());

		return result;
	}

	public static Result logout() {
		session().clear();
		FranceConnect fc = FranceConnect.getInstance();
		return redirect(fc.logout());
	}

	public static Result search(String prenoms) {
		Result result = badRequest();

		StringBuilder query = new StringBuilder();

		if (prenoms != null && !prenoms.isEmpty()) {
			query.append("prenoms:" + prenoms);
		}

		String accessToken = session().get("X-ACCESS-TOKEN");// request().getHeader("Authorization");

		FranceConnect fc = FranceConnect.getInstance();

		CheckToken ct = fc.checkToken(accessToken);
		if (ct != null) {
			result = ok(OpenDataSoft.askDataFranceConnect(ct, query.toString()));
		} else {
			ObjectNode node = Json.newObject();
			node.put("error",
					"need valid accessToken in 'Authorization' header");
			result = badRequest(node);
		}

		return result;
	}
}
