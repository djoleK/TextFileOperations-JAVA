package djole;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class MyFileOpenerClass {

	JFileChooser fileChooser = new JFileChooser();
	StringBuilder sb = new StringBuilder();

	public void pickMe() throws FileNotFoundException {

		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			File file = fileChooser.getSelectedFile();
			Scanner input = new Scanner(file);

			while (input.hasNext()) {
				sb.append(input.nextLine());
				sb.append("\n");

			}
			input.close();
		} else {
			sb.append("Nijedan fajl nije izabran.");
		}

	}

}
