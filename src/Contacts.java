
import java.io.Serializable;
import java.util.List;

public class Contacts implements Serializable {
	private int contactID;
	private String contactName;
	private String emailAddress;
	private List<String> contactNumber;

	public int getContactID() {
		return contactID;
	}

	public void setContactID(int contactID) {
		this.contactID = contactID;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public List<String> getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(List<String> contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Contacts(int contactID, String contactName, String emailAddress, List<String> contactNumber) {
		super();
		this.contactID = contactID;
		this.contactName = contactName;
		this.emailAddress = emailAddress;
		this.contactNumber = contactNumber;
	}

	public Contacts() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Contacts [contactID=" + contactID + ", contactName=" + contactName + ", emailAddress=" + emailAddress
				+ ", contactNumber=" + contactNumber + "]";
	}

}
