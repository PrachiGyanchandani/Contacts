import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import util.DbConnection;

public class ContactService {

	static Connection con = DbConnection.getConnection();

	public void addContact(Contacts contact, List<Contacts> contacts) {
		if (contacts.contains(contact)) {
			System.out.println("Contact already exist in the list.");
		} else {
			contacts.add(contact);
			System.out.println("Contact added successfully.");
		}
	}

	public void removeContact(Contacts contact, List<Contacts> contacts) throws ContactNotFoundException {
		if (contacts.contains(contact)) {
			contacts.remove(contact);
			System.out.println("Contact removed successfully.");
		} else {
			throw new ContactNotFoundException();
		}
	}

	public Contacts searchContactByName(String name, List<Contacts> contacts) throws ContactNotFoundException {
		for (Contacts c : contacts) {
			if (c.getContactName().equals(name)) {
				System.out.println("Contact found.");
				return c;
			}
		}
		throw new ContactNotFoundException();
	}

	public List<Contacts> SearchContactByNumber(String number, List<Contacts> contact) throws ContactNotFoundException {
		List<Contacts> cList = new ArrayList<>();

		for (Contacts c : contact) {
			List<String> numberList = c.getContactNumber();
			if (numberList == null) {
				continue;
			}

			for (String s : numberList) {
				if (s.contains(number)) {
					if (cList.contains(c))
						continue;
					else
						cList.add(c);
				}
			}
		}

		if (cList.size() == 0)
			throw new ContactNotFoundException();

		return cList;
	}

	public void addContactNumber(int contactId, String contactNo, List<Contacts> contacts)
			throws ContactNotFoundException {
		for (Contacts c : contacts) {
			if (c.getContactID() == contactId) {
				if (c.getContactNumber() != null) {
					c.getContactNumber().add(contactNo);
					System.out.println("Number added successfully.");
					return;
				} else {
					List<String> num = new ArrayList<>();
					num.add(contactNo);
					c.setContactNumber(num);
					return;
				}
			}
		}
		throw new ContactNotFoundException();
	}

	public class sortContactByNameComparator implements Comparator<Contacts> {
		@Override
		public int compare(Contacts o1, Contacts o2) {
			return o1.getContactName().compareTo(o2.getContactName());
		}
	}

	public void sortContactsByName(List<Contacts> contacts) {
		Collections.sort(contacts, new sortContactByNameComparator());
	}

	public void readContactsFromFile(List<Contacts> contacts, String fileName) throws IOException {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();

		while (line != null) {
			String[] data = line.split(",");

			if (data.length == 1)
				break;

			int id = Integer.parseInt(data[0]);
			String name = data[1];
			String email = data[2];
			List<String> temp = new ArrayList<>();
			for (int i = 3; i < data.length; i++) {
				temp.add(data[i]);
			}
			Contacts c = new Contacts(id, name, email, temp);
			contacts.add(c);
			line = br.readLine();
		}
	}

	public void serializeContactDetails(List<Contacts> contacts, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(contacts);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public List<Contacts> deserializeContact(String fileName) {
		List<Contacts> contacts = new ArrayList<>();
		File file = new File(fileName);
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			contacts = (List<Contacts>) ois.readObject();

			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return contacts;
	}

	public Set<Contacts> populateContactFromDb() throws SQLException {

		Set<Contacts> s = new HashSet<>();
		Statement smt = con.createStatement();
		ResultSet rs = smt.executeQuery("select * from contact_tbl");
		if (rs.next()) {
			do {
				int id = Integer.parseInt(rs.getString(1));
				String name = rs.getString(2);
				String email = rs.getString(3);
				String contact[] = null;
				List<String> ls = new ArrayList<>();
				if (!(rs.getString(4) == null)) {
					contact = rs.getString(4).split(",");

					for (int i = 0; i < contact.length; i++) {
						ls.add(contact[i]);
					}
				}

				Contacts c = new Contacts(id, name, email, ls);
				s.add(c);

			} while (rs.next());
		} else {
			System.out.println("Record Not Found.");
		}
		con.close();
		return s;
	}

	public Boolean addContacts(List<Contacts> existingContact, Set<Contacts> newContacts) {
		if (newContacts.size() == 0) {
			System.out.println("No new contacts to add.");
			return false;
		}
		existingContact.addAll(newContacts);
		System.out.println("New contacts added successfully.");
		return true;
	}
}
