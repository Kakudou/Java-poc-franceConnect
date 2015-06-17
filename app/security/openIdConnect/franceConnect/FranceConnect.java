package security.openIdConnect.franceConnect;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import play.Play;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import security.openIdConnect.OpenIdConnect;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FranceConnect extends OpenIdConnect
{
  private FranceConnectProvider franceConnectProvider;
  private static FranceConnect instance;
  
  public static FranceConnect getInstance(){
    if (instance == null){
      instance = new FranceConnect();
    }
    return instance;
  }
  
  public FranceConnect()
  {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = Play.application().getFile("./conf/FranceConnectProvider.json");
    try {
      franceConnectProvider = objectMapper.readValue(file, FranceConnectProvider.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public String getAuthUrl(String scopeComaSeparate)
  {
    return super.auth(franceConnectProvider, scopeComaSeparate);
  }
  
  public Token getToken(String code)
  {
    JsonNode jnode = super.token(franceConnectProvider, code).asJson();
    ObjectMapper om = new ObjectMapper();
    Token token = null;
    try {
      token = om.readValue(jnode.traverse(om), Token.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return token;
  }
  
  public IdentitePivot getIdentitePivot(String accessToken)
  {
    JsonNode jnode = super.userInfo(franceConnectProvider, accessToken).asJson();
    ObjectMapper om = new ObjectMapper();
    IdentitePivot idPivot = null;
    try {
      idPivot = om.readValue(jnode.traverse(om), IdentitePivot.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return idPivot;
  }
  
  public IdentitePivot fullAuth(String code)
  {
    Token token = this.getToken(code);
    Http.Context.current().session().put("X-ACCESS-TOKEN", token.getAccessToken());
    IdentitePivot idPivot = this.getIdentitePivot(token.getAccessToken());
    
    return idPivot;
  }
  
  public CheckToken checkToken(String accessToken)
  {
    CheckToken checkToken = null;
    HashMap<String, String> contents = new HashMap<>();
    
    contents.put("token", accessToken);
    
    WSResponse result = super.manualPostEndpoint(franceConnectProvider.getCheckTokenEndpoint(), contents, null);
    
    if (200 == result.getStatus()){
       JsonNode nodes = result.asJson();
      
      ObjectMapper om = new ObjectMapper();
      try {
        checkToken = om.readValue(nodes.traverse(om), CheckToken.class);
        checkToken.setAccessToken(accessToken);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return checkToken;
  }
  
  public String logout()
  {
    return franceConnectProvider.getRevocationEndpoint();
  }
}
