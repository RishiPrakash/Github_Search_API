package ti.blueoptima.ratelimiter.model;

public class Repository {
  private String name;
  private String url;
  private User owner;

  public Repository() {
  }

  public Repository(String name, String url, User owner) {
    this.name = name;
    this.url = url;
    this.owner = owner;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }
  @Override
  public String toString() {
    return "Repository{" +
        "name='" + name + '\'' +
        ", url='" + url + '\'' +
        ", owner=" + owner +
        '}';
  }
}
