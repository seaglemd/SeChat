package sechatlib;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class CreateAccountPanelController implements SeChatPanelManager{

	private MySQLDatabaseController tc = new MySQLDatabaseController();
	private PasswordControl pc;
	private GridBagConstraints c = new GridBagConstraints();
	private JTextField usernameTextField = new JTextField(20);
	private JPasswordField userPasswordField = new JPasswordField(20);
	private JPasswordField userRetypePasswordField = new JPasswordField(20);
	public CreateAccountPanelController(){
		setupPanel();
	}
	
	public void setupPanel(){
		createAccountPanel.setLayout(new GridBagLayout());
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		createAccountPanel.add(new JLabel("Desired Username: "), c);
		c.gridx = 1;
		createAccountPanel.add(usernameTextField, c);
		c.gridx = 0;
		c.gridy = 1;
		createAccountPanel.add(new JLabel("Password: "), c);
		c.gridx = 1;
		createAccountPanel.add(userPasswordField, c);
		c.gridx = 0;
		c.gridy = 2;
		createAccountPanel.add(new JLabel("Retype Password: "), c);
		c.gridx = 1;
		createAccountPanel.add(userRetypePasswordField, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		createAccountPanel.add(createButton, c);
		
		createAccountPanel.setVisible(false);
	}
	
	public boolean checkCreateCredentials() throws NoSuchAlgorithmException{
		String desiredUsername = usernameTextField.getText();
		tc.connectToDatabase();
		if(tc.checkForUsernameConflict(desiredUsername)){
			System.out.println("Username Good!");		
		}
		else{
			System.out.println("UserName already exists!");
			return false;
		}
		try{
			pc = new PasswordControl();
			if(!pc.comparePasswords(pc.getHash(Arrays.toString(userPasswordField.getPassword())),
					pc.getHash(Arrays.toString(userRetypePasswordField.getPassword())))){
				System.out.println("Passwords do not match");
				return false;
			}
		}catch(UnsupportedEncodingException ex){
			System.out.println("Message: " + ex.getMessage());
		}
		System.out.println("User can be created");
		return true;
	}
}
