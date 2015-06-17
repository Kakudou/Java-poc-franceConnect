package security.openIdConnect;

public abstract class AbstractProvider
{
  public abstract String getIssuer();
  public abstract String getClientId();
  public abstract String getClientSecret();
  public abstract String getCallback();
  public abstract String getAuthorizationEndpoint();
  public abstract String getResponseType();
  public abstract String getTokenEndpoint();
  public abstract String getUserInfoEndpoint();
  public abstract String getRevocationEndpoint();
}
