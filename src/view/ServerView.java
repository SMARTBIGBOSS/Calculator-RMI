package view;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.HeadlessException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method Invocation - Calculator
 * 
 * Description: The server view of calculator implements all methods of the calculator interface.
 */

public class ServerView extends JFrame{
	// Text area for displaying contents 
	private JTextArea jta = new JTextArea();

	// The constructor of server class
	public ServerView() {
		setComponents();
	}
	
	// Function to set components of the Server Frame
	private void setComponents() {
		jta.setFont(new Font("Dialog",1,18));
		
		// Place text area on the frame 
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);

		setTitle("Server");
		setSize(800, 600);
		this.setLocation(850, 400);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	// Add the content of Text area
	public void setJta(String info) {
		this.jta.append(info);
	}

}
