package security;

import play.libs.F.Promise;
import play.mvc.Action;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Results;
import security.openIdConnect.franceConnect.CheckToken;
import security.openIdConnect.franceConnect.FranceConnect;

public class SecuredAction extends Action.Simple {
	@Override
	public Promise<Result> call(Context ctx) throws Throwable {
		String token = getTokenFromSession(ctx);
		if (token != null) {
			FranceConnect fc = FranceConnect.getInstance();
			CheckToken ct = fc.checkToken(token);
			if (ct != null){
				return delegate.call(ctx);
			}
		}
		Result unauthorized = Results.redirect("http://localhost:9000");
		return Promise.pure(unauthorized);
	}

	private String getTokenFromSession(Context ctx) {
		String authTokenHeaderValues = ctx.session().get("X-ACCESS-TOKEN");
		return authTokenHeaderValues;
	}


}