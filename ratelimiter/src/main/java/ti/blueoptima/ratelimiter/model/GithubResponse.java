package ti.blueoptima.ratelimiter.model;

import java.util.List;

class GitHubResponse<T> {
  private List<T> items;

  public GitHubResponse() {
  }

  public GitHubResponse(List<T> items) {
    this.items = items;
  }

  public List<T> getItems() {
    return this.items;
  }

  public void setItems(List<T> items) {
    this.items = items;
  }

 @Override
  public String toString() {
    return "GitHubResponse{" +
        "items=" + items +
        '}';
  }
}