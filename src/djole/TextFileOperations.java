package djole;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class TextFileOperations {

	private JFrame frame;
	private JTextField brojReci;
	private JTextField filePath;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TextFileOperations window = new TextFileOperations();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	/**
	 * Create the application.
	 */
	public TextFileOperations() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 825, 445);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(29, 71, 396, 195);
		frame.getContentPane().add(textArea);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);

		JButton btnUitajFajl = new JButton("Učitaj fajl");
		btnUitajFajl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MyFileOpenerClass myobj = new MyFileOpenerClass();

				try {
					myobj.pickMe();
				} catch (Exception e) {
					e.printStackTrace();
				}
				textArea.setText(myobj.sb.toString());
				filePath.setText(myobj.fileChooser.getSelectedFile().getAbsolutePath());
				textArea.setEditable(false);

			}
		});
		btnUitajFajl.setBounds(491, 7, 132, 25);
		frame.getContentPane().add(btnUitajFajl);

		JButton btnSrediRazmake = new JButton("Sredi razmake");
		btnSrediRazmake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(srediRazmake(textArea));
				textArea.setEditable(false);

			}
		});
		btnSrediRazmake.setBounds(491, 54, 132, 25);
		frame.getContentPane().add(btnSrediRazmake);

		JButton btnPrebrojRei = new JButton("Prebroj reči");
		btnPrebrojRei.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(srediRazmake(textArea));
				brojReci.setText(String.valueOf(prebrojReci(textArea)));
				textArea.setEditable(false);
			}
		});
		btnPrebrojRei.setBounds(491, 104, 132, 25);
		frame.getContentPane().add(btnPrebrojRei);

		JLabel lblBrojRei = new JLabel("Broj reči:");
		lblBrojRei.setBounds(27, 278, 66, 15);
		frame.getContentPane().add(lblBrojRei);

		brojReci = new JTextField();
		brojReci.setEditable(false);
		brojReci.setBounds(124, 276, 124, 36);
		frame.getContentPane().add(brojReci);
		brojReci.setColumns(10);

		JButton btnPalindrom = new JButton("Palindrom?");
		btnPalindrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isPalindrome(toWord(srediRazmake(textArea)))) {
					JOptionPane.showMessageDialog(null, "Rečenica koju ste uneli jeste palindrom!", "Jeste Palindrom!",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Rečenica koju ste uneli nije palindrom!", "Nije Palindrom!",
							JOptionPane.INFORMATION_MESSAGE);
				}
				textArea.setEditable(false);
			}
		});
		btnPalindrom.setBounds(491, 152, 132, 25);
		frame.getContentPane().add(btnPalindrom);

		filePath = new JTextField();
		filePath.setEditable(false);
		filePath.setBounds(36, 10, 389, 36);
		frame.getContentPane().add(filePath);
		filePath.setColumns(10);

		JButton btnSave = new JButton("Sačuvaj");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sredjen = srediRazmake(textArea);
				int brojReci = prebrojReci(textArea);
				boolean palindrom = isPalindrome(toWord(srediRazmake(textArea)));

				JFileChooser file_chooser = new JFileChooser();

				if (file_chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = file_chooser.getSelectedFile();

					try {
						if (!file.exists()) {
							file.createNewFile();
						}
						String output = sredjen;
						String prikaziBrojReci = "Broj reči u tekstu je: " + String.valueOf(brojReci);
						String daLiJePalindrom = "Ako je true, onda jeste, ako je false onda nije: "
								+ String.valueOf(palindrom);
						List<String> lista = new ArrayList<>();
						lista.add(output);
						lista.add(prikaziBrojReci);
						lista.add(daLiJePalindrom);
						Files.write(file.toPath(), lista, StandardOpenOption.APPEND);

					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnSave.setBounds(491, 198, 132, 25);
		frame.getContentPane().add(btnSave);

	}

	public static String srediRazmake(JTextArea textArea) {
		String after = textArea.getText().trim().replaceAll(" +", " "); // mora malo regEx i google, lakše je i čitkije
		return after;
	}

	public static int prebrojReci(JTextArea textArea) {
		int count = 0;
		String zaObradu = textArea.getText();
		for (int i = 0; i < zaObradu.length(); i++) {
			if (zaObradu.charAt(i) == ' ') { // metod charAt je ovde da ostane i u JAVI 12 ako treba
				count++;
			}
		}
		return count + 1;

	}

	public static String toWord(String sentence) {
		String recenica = "";
		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.charAt(i) == ' ') {
				recenica += "";
			} else {
				recenica += sentence.charAt(i);
			}
		}
		return recenica.toLowerCase();
	}

	public static boolean isPalindrome(String word) {

		for (int i = 0; i < word.length() / 2; i++) {
			if (word.charAt(i) != word.charAt(word.length() - 1 - i)) {
				return false;
			}
		}
		return true;
	}
}
