package security.openIdConnect.franceConnect;

import security.openIdConnect.franceConnect.json.CheckTokenJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


@JsonDeserialize(using = CheckTokenJsonDeserializer.class)
public class CheckToken
{
  private String accessToken, identity, scope, client, identityProviderHost, identityProviderId, acr;
  
  public CheckToken(String identity, String scope, String client, String identity_provider_host,
      String identity_provider_id, String acr)
  {
    this.identity = identity;
    this.scope = scope;
    this.client = client;
    this.identityProviderHost = identity_provider_host;
    this.identityProviderId = identity_provider_id;
    this.acr = acr;
  }
  
  public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}
  
  public String getAccess_token()
  {
    return accessToken;
  }
  
  public String getIdentity()
  {
    return identity;
  }
  
  public String getScope()
  {
    return scope;
  }
  
  public String getClient()
  {
    return client;
  }
  
  public String getIdentity_provider_host()
  {
    return identityProviderHost;
  }
  
  public String getIdentity_provider_id()
  {
    return identityProviderId;
  }
  
  public String getAcr()
  {
    return acr;
  }
}
