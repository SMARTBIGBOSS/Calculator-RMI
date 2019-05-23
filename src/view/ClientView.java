package view;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method Invocation - Calculator
 * 
 * Description: The client view of calculator allows to select operands and operator and submits requests.
 */

public class ClientView extends JFrame {
	// Text field to show operands
	private JTextField jtf = new JTextField();

	// Text area to display calculator result
	private JTextArea jta = new JTextArea();

	// Strings of buttons's text
	final String[] str = { 
			"7", "8", "9", "/",
			"4", "5", "6", "*",
			"1", "2", "3", "-",
			"0", "c", "=", "+"
	};

	// Buttons to hold the operands and operators
	private JButton buttons[] = new JButton[str.length];

	// Class constructor
	public ClientView() {
		setComponents();
	}

	// Function to set components of the Client Frame
	private void setComponents() {
		// Panel to hold buttons
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new GridLayout(4, 4, 5, 5));
		subPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		for (int i = 0; i < str.length; i++) {
			buttons[i] = new JButton(str[i]);
			subPanel.add(buttons[i]);
		}

		// Set properties of Text field
		jtf.setFont(new Font("Dialog", 1, 25));
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(jtf, BorderLayout.NORTH);
		panel.add(subPanel, BorderLayout.CENTER);

		jta.setFont(new Font("Dialog",1,18));
		
		// Frame to set properties
		this.setLayout(new BorderLayout());
		this.add(panel, BorderLayout.NORTH);
		this.add(new JScrollPane(jta), BorderLayout.CENTER);
		this.setTitle("Client");
		this.setSize(350, 550);
		this.setResizable(false);
		this.setLocation(300, 400);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	// Function to handle the listener
	public void addCalculateLiserner(ActionListener listenForButton) {
		for (JButton button : buttons) {
			button.addActionListener(listenForButton);
		}
	}

	// Set Text Area content
	public void setJta(String string) {
		this.jta.append(string);
	}

	// Set Text Field content
	public void setJtf(String jtfstr) {
		this.jtf.setText(jtfstr);
	}

}
