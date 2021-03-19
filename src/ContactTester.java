import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactTester {
	public static void main(String[] args) throws IOException, ContactNotFoundException, SQLException {

		ContactService sc = new ContactService();

		List<Contacts> contacts = new ArrayList<>();

		Contacts c1 = new Contacts(4, "Prachi", "prachi@gmail.com", null);
		sc.addContact(c1, contacts);

		String ContactFile = "F:\\Persistent Training\\Java\\Ass10-Prachi_Gyanchandani\\contact.txt";
		String serializedFile = "F:\\Persistent Training\\Java\\Ass10-Prachi_Gyanchandani\\serializedFile.txt";

		sc.readContactsFromFile(contacts, ContactFile);
		System.out.println(contacts);
		sc.removeContact(c1, contacts);
		sc.searchContactByName("Radha", contacts);
		System.out.println(sc.SearchContactByNumber("456", contacts));
		sc.addContactNumber(3, "676767", contacts);
		sc.sortContactsByName(contacts);
		System.out.println(contacts);
		sc.serializeContactDetails(contacts, serializedFile);
		sc.deserializeContact(serializedFile);

		System.out.println(sc.populateContactFromDb());
	}
}
