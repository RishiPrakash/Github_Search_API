
package ti.blueoptima.ratelimiter.model;

public class ResourceLimit {
  private RateLimit core;
  private RateLimit search;

  public ResourceLimit() {
  }

  public ResourceLimit(RateLimit core, RateLimit search) {
    this.core = core;
    this.search = search;
  }

  public RateLimit getCore() {
    return core;
  }

  public void setCore(RateLimit core) {
    this.core = core;
  }

  public RateLimit getSearch() {
    return search;
  }

  public void setSearch(RateLimit search) {
    this.search = search;
  }

  @Override
  public String toString() {
    return "ResourceLimit{" +
        "core=" + core +
        ", search=" + search +
        '}';
  }
}
