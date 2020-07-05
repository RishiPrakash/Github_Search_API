package ti.blueoptima.ratelimiter.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"repoName","totalCommit"})
public class TotalCommit {
	@JsonProperty("totalCommit")
	private int totalCommit;
	@JsonProperty("repoName")
	private String repoName;
	
	public TotalCommit(int total,String repo) {
		this.totalCommit = total;
		this.repoName = repo;
	}
	
	
	public int getTotalCommit() {
		return totalCommit;
	}
	public void setTotalCommit(int totalCommit) {
		this.totalCommit = totalCommit;
	}
	public String getRepoName() {
		return repoName;
	}
	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}
	@Override
	public String toString() {
		return "TotalCommit [totalCommit=" + totalCommit + ", repoName=" + repoName + "]";
	}
	
}
