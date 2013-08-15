package rsa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;


public class RSAUserInterface extends JFrame {

	private static final long serialVersionUID = 4192081049915088589L;
	
	private static final int textAreaColumns = 40;
	private static final int textAreaRows = 5;

	private JSplitPane splitPane;
	private JTabbedPane tabPane;
	private JTextArea modulusJTA, publicKeyJTA, privateKeyJTA, encryptInputJTA,
			encryptOutputJTA, decryptInputJTA, decryptOutputJTA,
			generatePublicKeyJTA, generatePrivateKeyJTA;
	private JScrollPane modulusJSP;
	private JButton encryptJB, decryptJB, generateJB;

	public static void main(String[] args) {
		RSAUserInterface application = new RSAUserInterface();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public RSAUserInterface() {
		makeAllTextAreas();
		assembleUI();
	}
	
	private void makeAllTextAreas() {
		publicKeyJTA = makeTextArea("Public Key");
		encryptInputJTA = makeTextArea("Plaintext Message");
		encryptOutputJTA = makeTextArea("Encrypted Message");
		
		privateKeyJTA = makeTextArea("Private Key");
		decryptInputJTA = makeTextArea("Encrypted Message");
		decryptOutputJTA = makeTextArea("Decrypted Message");
		
		generatePublicKeyJTA = makeTextArea("New Public Key");
		generatePrivateKeyJTA = makeTextArea("New Private Key");
		
		modulusJTA = makeTextArea("Encryption Modulus");
	}

	private JTextArea makeTextArea(String title) {
		JTextArea ta = new JTextArea(textAreaRows, textAreaColumns);
		ta.setLineWrap(true);
		ta.setBorder(BorderFactory.createTitledBorder(title));
		return ta;
	}

	private JScrollPane assembleEncryptionScrollPane() {		
		JPanel encryptPanel = new JPanel();
		encryptPanel.setLayout(new BoxLayout(encryptPanel, BoxLayout.Y_AXIS));
		
		encryptJB = new JButton("Encrypt Message");
		encryptJB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RSAEncryptor rsa = new RSAEncryptor();
				
				BigInteger plainText = rsa.messageToInteger(encryptInputJTA.getText());
				BigInteger publicKey = KeySet.parseInt(publicKeyJTA.getText());
				BigInteger modulus = KeySet.parseInt(modulusJTA.getText());
				BigInteger result = rsa.encrypt(plainText, publicKey, modulus);
				
				encryptOutputJTA.setText(KeySet.intString(result));
			}
		});
				
		encryptPanel.add(publicKeyJTA);
		encryptPanel.add(encryptInputJTA);
		encryptPanel.add(encryptJB);
		encryptPanel.add(encryptOutputJTA);
		
		return new JScrollPane(encryptPanel);
	}
	
	private JScrollPane assembleDecryptionScrollPane() {
		JPanel decryptPanel = new JPanel();
		decryptPanel.setLayout(new BoxLayout(decryptPanel, BoxLayout.Y_AXIS));
		
		decryptJB = new JButton("Decrypt Message");
		decryptJB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				RSAEncryptor rsa = new RSAEncryptor();
				
				BigInteger cipherText = KeySet.parseInt(decryptInputJTA.getText());
				BigInteger privateKey = KeySet.parseInt(privateKeyJTA.getText());
				BigInteger modulus = KeySet.parseInt(modulusJTA.getText());
				
				decryptOutputJTA.setText(rsa.integerToMessage(
						rsa.decrypt(cipherText, privateKey, modulus)));
			}
		});
				
		decryptPanel.add(privateKeyJTA);
		decryptPanel.add(decryptInputJTA);
		decryptPanel.add(decryptJB);
		decryptPanel.add(decryptOutputJTA);
		
		return new JScrollPane(decryptPanel);
	}

	private JScrollPane assembleKeyGeneratorScrollPane() {
		JPanel generatorPanel = new JPanel();
		generatorPanel.setLayout(new BoxLayout(generatorPanel, BoxLayout.Y_AXIS));

		generateJB = new JButton("Generate Keys");
		generateJB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				KeySet set = new RSAKeyGenerator().generateKeys();
				
				modulusJTA.setText(KeySet.intString(set.modulus));
				generatePublicKeyJTA.setText(KeySet.intString(set.publicKey));
				generatePrivateKeyJTA.setText(KeySet.intString(set.privateKey));
			}
		});
		
		generatorPanel.add(generateJB);
		generatorPanel.add(new JLabel("This may take a minute..."));
		generatorPanel.add(generatePublicKeyJTA);
		generatorPanel.add(generatePrivateKeyJTA);
		
		return new JScrollPane(generatorPanel);
	}
	
	private void assembleUI() {		
		tabPane = new JTabbedPane();
		tabPane.addTab("Encrypt", assembleEncryptionScrollPane());
		tabPane.addTab("Decrypt", assembleDecryptionScrollPane());
		tabPane.addTab("Generate Keys", assembleKeyGeneratorScrollPane());
		
		modulusJSP = new JScrollPane(modulusJTA);
		splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, modulusJSP, tabPane);
		add(splitPane);
		
		setTitle("RSA Encryption");
		pack();
		setVisible(true);
	}

}
