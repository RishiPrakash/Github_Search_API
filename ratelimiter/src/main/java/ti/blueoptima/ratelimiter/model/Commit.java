package ti.blueoptima.ratelimiter.model;


public class Commit {
  private String htmlUrl;
  private User author;
  private Repository repository;

  public Commit() {
  }

  public Commit(String htmlUrl, User author, Repository repository) {
    this.htmlUrl = htmlUrl;
    this.author = author;
    this.repository = repository;
  }

  public String getHtmlUrl() {
    return htmlUrl;
  }

  public void setHtmlUrl(String htmlUrl) {
    this.htmlUrl = htmlUrl;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Repository getRepository() {
    return repository;
  }

  public void setRepository(Repository repository) {
    this.repository = repository;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Commit commit = (Commit) o;

    if (!htmlUrl.equals(commit.htmlUrl)) return false;
    if (!author.equals(commit.author)) return false;
    return repository.equals(commit.repository);
  }

  @Override
  public int hashCode() {
    int result = htmlUrl.hashCode();
    result = 31 * result + author.hashCode();
    result = 31 * result + repository.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Commit{" +
        "htmlUrl='" + htmlUrl + '\'' +
        ", author=" + author +
        ", repository=" + repository +
        '}';
  }
}
