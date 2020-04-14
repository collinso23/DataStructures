/**
 * A program to read in and modify password files using salted SHA-512 hashes
 */
package edu.wit.cs.comp2000;

import sun.plugin2.main.client.MessagePassingOneWayJSObject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class UserLogin {

	static Map<String, Password> userTable;	// stores all relevant info about authentication
	private static final Random RandomObj = new SecureRandom();

	/**
	 * Reads lines from the userFile, parses them, and adds the results to the userTable
	 *
	 * @param userFile A File object to read from
	 */
	// reads properly formatted userFile into userTable
	public static void ReadFile(File userFile) throws IOException {
			userTable = new HashMap<>();
			String line;
			String delim = ":";
			Path path = Paths.get(userFile.getAbsolutePath());
			BufferedReader br=null;
			try {
				br = new BufferedReader(new FileReader(userFile));
			} catch (FileNotFoundException e){
				e.printStackTrace();
			}
			while ((line = br.readLine())!=null){
				String[] parts=line.split(delim,3);
				if (parts.length >= 3){
					String key = parts[0];
					Password value = new Password(parts[1],parts[2]);
					userTable.put(key,value);
				} else {
					System.out.println("Empty line: "+line);
				}
			}
			br.close();
	}

	/**
	 * Iterates through all key values in userTable and outputs lines
	 * to userFile formatted like user:salt:hash
	 *
	 * @param userFile A File object that the table should be written to
	 */

	// create a copy of current file to allow for rollback if update fails
    // create the userDB file in path of the current file
    // write userTable to userDB
	public static void WriteFile(File userFile) throws IOException {
		//Get file path of currently userFile
		Path filePath = Paths.get(userFile.getAbsolutePath());
		String baseDir = filePath.getParent().toString();

		//init userDB to store all account information
		File database = new File(baseDir+"\\userDB");

		//init backup of current userFile
		File backup = new File(filePath+"_backup");
		Files.copy(userFile.toPath(),backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
		try {
			Path dbPath = Paths.get(database.getAbsolutePath());
			Files.write(dbPath, () -> userTable.entrySet().stream()
					.<CharSequence>map(e -> e.getKey() + ":" + e.getValue().getSalt() + ":" + e.getValue().getHash())
					.iterator());
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	/**
	 * Prompts the user for a username/password and attempts to add the user
	 * to the userTable. Fails if the user is already present in the table.
	 *
	 * @param s A Scanner to read from the console
	 * @return boolean based on if the user credentials are added to table
	 */
	// Checks if user if in DB already, then adds New Users to DB
    // check if key in usertables
    // if not create a new salt and hash it with new password
    // save user:pword into userTables
	public static boolean AddUser(Scanner s) {
		System.out.print("Time to add a new user!\n" + "Name must be unique and not contain ':'\n");
		System.out.print("Enter username: ");
		String name = s.nextLine();
		if (validUser(name)) {
			System.out.print("Enter a password: ");
			String newPassword = s.nextLine();
			String newSalt = genSalt();
			Password pword = new Password(newSalt, genHash(newSalt + newPassword));
			userTable.put(name, pword);
			return true;
		} else
			return false;
	}

	/**
	 * Prompts the user for a username/password and checks the userTable for
	 * the resulting combination
	 *
	 * @param s A Scanner to read from the console
	 * @return boolean based on if the user credentials are accurate
	 */
    // get key based off uname (return error if no key present)
    // hash pword with salt from usertables(uname)
    // compared salt+pword hash with hash stored in key.
	public static boolean Login(Scanner s) {
		try {
			System.out.print("Enter Username: ");
			String uname = s.nextLine();
			if (userTable.containsKey(uname)) {
				System.out.print("Enter Password: ");
				String lpword = s.nextLine();
				Password storedPassword = userTable.get(uname);
				String storedSalt = storedPassword.getSalt();

				//gen hash of lpword to check against uname.hash
				String hashedLPword = genHash(storedSalt + lpword);

				if (hashedLPword.equals(storedPassword.getHash()))
					return true;
			}
			else
				System.out.println("USER "+uname+" NOT FOUND!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	//checks if a username entered follows naming convention and if uname in hashtable
	private static boolean validUser(String user) {
		if (user.contains(":")) {
			System.out.println("Invalid: " + user + " doesn't follow naming conventions");
			return false;
		} else if (userTable.containsKey(user)) {
			System.out.println("Invalid key: " + user);
			return false;
		}
		return true;
	}

    //prints out userTable to see entries currently in memory
    private static void printTable(){
        userTable.forEach((key, value) -> System.out.println(key + ":" + value.salt + ":" + value.hash));
    }

	/**
	 * Generates a salt value based on the SecureRandom object
	 * 
	 * @return Returns an 8-character string that represents a random value
	 */
	private static String genSalt() {
		byte[] salt = new byte[8];
		RandomObj.nextBytes(salt);
		return byteArrayToStr(salt);
	}
	
	/**
	 * Converts an array of bytes to the corresponding hex String
	 * 
	 * @param b An array of bytes
	 * @return A String that represents the array of bytes in hex
	 */
	private static String byteArrayToStr(byte[] b) {
		StringBuffer hexHash =  new StringBuffer();
		for (byte value : b) {
			String hexChar = Integer.toHexString(0xff & value);
			if (hexChar.length() == 1) {
				hexHash.append('0');
			} // end if
			hexHash.append(hexChar);
		} // end for
		return hexHash.toString();
	}

	/**
	 * Generates a hash for a given String
	 * 
	 * @param p A String to calculate the hash from
	 * @return The hash value as a String
	 */
	private static String genHash(String p) {
		// Create the MessageDigest object
		MessageDigest myDigest = null;
		try {
			myDigest = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("SHA-512 not available");
			System.exit(1);
		}
		// Update the object with the hash of ‘someStringToHash’
		myDigest.update(p.getBytes());
		
		// Get the SHA-512 hash from the object
		byte hashCode[] = myDigest.digest();

		return byteArrayToStr(hashCode);
	}

	
	public static void main(String[] args) throws IOException {

		Scanner s = new Scanner(System.in);

		System.out.print("Enter user file: ");
		File userFile = new File(s.nextLine());
		ReadFile(userFile);

		while (true) {
			System.out.print ("Would you like to (L)og in, (A)dd a new user, (T)est, or (Q)uit? ");
			char choice = s.nextLine().charAt(0);
			switch (choice) {
			case 'L':
				if (Login(s))
					System.out.println("Login successful.");
				else
					System.out.println("Username and password did not match.");
				break;
			case 'A':
				if (AddUser(s)) {
					System.out.println("User successfully added.");
				} else
					System.out.println("User not added.");
				break;
			case 'T':
				System.out.println("What would you like to test?\n"
						+"(p)athAndTable, (r)ead, (a)dd, (w)rite");
				char tchoice = s.nextLine().charAt(0);
				switch (tchoice) {
					case 'p':
						Path path = Paths.get(userFile.getAbsolutePath());
						String baseDir = path.getParent().toString();
						System.out.println(path);
						System.out.println(baseDir);
						printTable();
						break;
					case 'r':
						try {
							ReadFile(userFile);
							printTable();
						} catch (Exception e){
							e.printStackTrace();
						} //testing: ReadFile !throw IO or FileNotFound exception
						break;
					case 'a':
						try {
							AddUser(s);
						} catch (Exception e) {
							e.printStackTrace();
						} //testing: AddUser does not break program
						break;
					case 'w':
						try {
							WriteFile(userFile);
							printTable();
						} catch (Exception e){
							e.printStackTrace();
						} //testing: WriteFile !throw IO or FileNotFound
						break;
				}
				break;
			case 'Q':
				WriteFile(userFile);
				s.close();
				System.out.println("Exiting.");
				System.exit(0);
			}
		}

	}

}
