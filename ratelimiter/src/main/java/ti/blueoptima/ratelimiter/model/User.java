package ti.blueoptima.ratelimiter.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class User {
  private int id;
  private String login;
  private String url;
  private String avatar_url;
 

public User() {
  }

  public User(int id, String login, String url) {
    this.id = id;
    this.login = login;
    this.url = url;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
  @Override
public String toString() {
	return "User [id=" + id + ", login=" + login + ", url=" + url + ", avatar_url=" + avatar_url + "]";
}

public String getAvatar_url() {
	return avatar_url;
}

public void setAvatar_url(String avatar_url) {
	this.avatar_url = avatar_url;
}
}


