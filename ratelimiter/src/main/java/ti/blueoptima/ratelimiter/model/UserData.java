package ti.blueoptima.ratelimiter.model;

public class UserData {

	private String firstName;
	private String lastName;
	private String location;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Override
	public String toString() {
		return "UserData [firstName=" + firstName + ", lastName=" + lastName + ", location=" + location + "]";
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
