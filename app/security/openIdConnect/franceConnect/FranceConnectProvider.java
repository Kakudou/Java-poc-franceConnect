package security.openIdConnect.franceConnect;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import security.openIdConnect.AbstractProvider;
import security.openIdConnect.franceConnect.json.FranceConnectProviderJsonDeserializer;

@JsonDeserialize(using = FranceConnectProviderJsonDeserializer.class)
public class FranceConnectProvider extends AbstractProvider
{
  
  private String issuer, clientId, clientSecret, callback, authorizationEndpoint, tokenEndpoint, userinfoEndpoint, logoutEndpoint, checktokenEndpoint, responseType;

  public FranceConnectProvider(String issuer, String clientId, String clientSecret, String callback,
      String authorizationEndpoint, String tokenEndpoint, String userinfoEndpoint, String logoutEndpoint,
      String checktokenEndpoint, String responseType)
  {
    this.issuer = issuer;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.callback = callback;
    this.authorizationEndpoint = authorizationEndpoint;
    this.tokenEndpoint = tokenEndpoint;
    this.userinfoEndpoint = userinfoEndpoint;
    this.logoutEndpoint = logoutEndpoint;
    this.checktokenEndpoint = checktokenEndpoint;
    this.responseType = responseType;
  }

  @Override
  public String getIssuer()
  {
    return this.issuer;
  }

  @Override
  public String getClientId()
  {
    return this.clientId;
  }

  @Override
  public String getClientSecret()
  {
    return this.clientSecret;
  }

  @Override
  public String getCallback()
  {
    return this.callback;
  }

  @Override
  public String getAuthorizationEndpoint()
  {
    return this.authorizationEndpoint;
  }

  @Override
  public String getResponseType()
  {
    return this.responseType;
  }

  @Override
  public String getTokenEndpoint()
  {
    return this.tokenEndpoint;
  }

  @Override
  public String getUserInfoEndpoint()
  {
    return this.userinfoEndpoint;
  }

  @Override
  public String getRevocationEndpoint()
  {
    return this.logoutEndpoint;
  }

  public String getCheckTokenEndpoint()
  {
    return this.checktokenEndpoint;
  }
}
