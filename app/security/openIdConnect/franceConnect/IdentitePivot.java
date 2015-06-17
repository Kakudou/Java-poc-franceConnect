package security.openIdConnect.franceConnect;

import security.openIdConnect.franceConnect.json.IdentitePivotJsonDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = IdentitePivotJsonDeserializer.class)
public class IdentitePivot
{
  private String givenName, familyName, birthdate, gender, birthplace, birthcountry;

  public IdentitePivot(String givenName, String familyName, String birthdate, String gender, String birthplace,
      String birthcountry)
  {
    this.givenName = givenName;
    this.familyName = familyName;
    this.birthdate = birthdate;
    this.gender = gender;
    this.birthplace = birthplace;
    this.birthcountry = birthcountry;
  }

  public String getGivenName()
  {
    return givenName;
  }

  public String getFamilyName()
  {
    return familyName;
  }

  public String getBirthdate()
  {
    return birthdate;
  }

  public String getGender()
  {
    return gender;
  }

  public String getBirthplace()
  {
    return birthplace;
  }

  public String getBirthcountry()
  {
    return birthcountry;
  }
  
}
