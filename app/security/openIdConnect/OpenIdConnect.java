package security.openIdConnect;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

import play.libs.F.Promise;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;
import play.mvc.Http;

public abstract class OpenIdConnect {

	public static WSResponse manualGetEndpoint(String endpoint,
			HashMap<String, String> queryParams,
			HashMap<String, String> headerParams) {
		WSRequestHolder ws = WS.url(endpoint);

		if (queryParams != null && !queryParams.isEmpty()) {
			for (String key : queryParams.keySet()) {
				ws.setQueryParameter(key, queryParams.get(key));
			}
		}

		if (headerParams != null && !headerParams.isEmpty()) {
			for (String key : headerParams.keySet()) {
				ws.setHeader(key, headerParams.get(key));
			}
		}

		Promise<WSResponse> response = ws.setFollowRedirects(true).get();

		return response.get(5000);
	}

	public static WSResponse manualPostEndpoint(String endpoint,
			HashMap<String, String> contents,
			HashMap<String, String> headerParams) {

		WSRequestHolder ws = WS.url(endpoint);

		StringBuilder content = new StringBuilder();
		if (contents != null && !contents.isEmpty()) {
			for (String key : contents.keySet()) {
				content.append(key + "=" + contents.get(key));
				content.append("&");
			}
		}

		if (headerParams != null && !headerParams.isEmpty()) {
			for (String key : headerParams.keySet()) {
				ws.setHeader(key, headerParams.get(key));
			}
		}

		Promise<WSResponse> response = ws
				.setContentType("application/x-www-form-urlencoded")
				.post(content.toString());

		return response.get(5000);
	}

	protected String auth(AbstractProvider provider, String scopeComaSeparate) {

		StringBuilder authUrl = new StringBuilder();

		authUrl.append(provider.getAuthorizationEndpoint());
		authUrl.append("?");

		StringBuilder queryParams = new StringBuilder();

		queryParams.append("response_type=" + provider.getResponseType());
		queryParams.append("&client_id=" + provider.getClientId());
		queryParams.append("&redirect_uri=" + provider.getCallback());

		StringBuilder craftScope = new StringBuilder();
		for (String scope : scopeComaSeparate.split(",")) {
			craftScope.append(scope);
			craftScope.append("+");
		}

		queryParams.append("&scope="
				+ craftScope.substring(0, craftScope.length() - 1));

		String state = (new BigInteger(130, new SecureRandom())).toString(32);
		queryParams.append("&state=" + state);
		Http.Context.current().session().put("state", state);

		String nonce = (new BigInteger(50, new SecureRandom())).toString(16);
		queryParams.append("&nonce=" + nonce);

		authUrl.append(queryParams.toString());

		return authUrl.toString();
	}

	protected WSResponse token(AbstractProvider provider, String code) {
		HashMap<String, String> contents = new HashMap<>();

		contents.put("code", code);
		contents.put("client_secret", provider.getClientSecret());
		contents.put("client_id", provider.getClientId());
		contents.put("redirect_uri", provider.getCallback());
		contents.put("grant_type", "authorization_code");

		WSResponse response = OpenIdConnect.manualPostEndpoint(
				provider.getTokenEndpoint(), contents, null);
		return response;
	}

	protected WSResponse userInfo(AbstractProvider provider, String accessToken) {
		HashMap<String, String> headerParams = new HashMap<>();

		headerParams.put("Authorization", "Bearer " + accessToken);

		WSResponse response = OpenIdConnect.manualGetEndpoint(
				provider.getUserInfoEndpoint(), null, headerParams);

		return response;
	}

}
