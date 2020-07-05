package ti.blueoptima.ratelimiter.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInformation {
	@JsonProperty("public_information")
	private User user;
	 @JsonProperty("contributed_to")
	  private List<TotalCommit> repoInformation;
	 
	 public UserInformation(User u,List<TotalCommit> t) {
		 this.user = u;
		 this.repoInformation = t;
	 }
	 
	 
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<TotalCommit> getRepoInformation() {
		return repoInformation;
	}
	public void setRepoInformation(List<TotalCommit> repoInformation) {
		this.repoInformation = repoInformation;
	}
	 
	 

}
