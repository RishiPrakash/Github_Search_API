package ti.blueoptima.ratelimiter.model;



public class GitHubRateLimitResponse {

  private ResourceLimit resources;

  public GitHubRateLimitResponse() {
  }

  public ResourceLimit getResources() {
    return this.resources;
  }

  public void setResources(ResourceLimit resources) {
    this.resources = resources;
  }

  @Override
  public String toString() {
    return "GitHubRateLimitResponse{" +
        "resources=" + resources +
        '}';
  }
}