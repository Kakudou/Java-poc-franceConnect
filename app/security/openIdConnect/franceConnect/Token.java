package security.openIdConnect.franceConnect;

import security.openIdConnect.franceConnect.json.TokenJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = TokenJsonDeserializer.class)
public class Token
{
  private String accessToken, tokenType, expiresIn, idToken;

  public Token(String accessToken, String tokenType, String expiresIn, String idToken)
  {
    super();
    this.accessToken = accessToken;
    this.tokenType = tokenType;
    this.expiresIn = expiresIn;
    this.idToken = idToken;
  }

  public String getAccessToken()
  {
    return accessToken;
  }

  public String getTokenType()
  {
    return tokenType;
  }

  public String getExpiresIn()
  {
    return expiresIn;
  }

  public String getIdToken()
  {
    return idToken;
  }
  
}
