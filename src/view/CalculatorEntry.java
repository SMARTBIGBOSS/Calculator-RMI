package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import controller.ClientController;
import controller.ServerController;
import model.Model;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method invocation
 * 
 * Description: The entrance of the program. 
 *              It handles the client view and server view.
 */

public class CalculatorEntry extends JFrame{
	// Label to show the title
	private JLabel jlb = new JLabel("Enter to Client");

	// Button to open client views
	private JButton jbtClient = new JButton("Add Client");
	
	// Button to open server views 
	private JButton jbtServer = new JButton("Strat Server");
	
	// Declare Server view
	private ServerView s;
	
	// Declare Client view
	private ClientView c;
	
	// Declare Model
	private Model m;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CalculatorEntry();
		
	}
	
	// Class constructor
	public CalculatorEntry() {
		setComponents();
		
		// Create a server view
		s = new ServerView();
		
		// Create a model to hold calculation results
		m = new Model();
	}
	
	// Function to set components of the Entrance Frame
	private void setComponents() {
		
		// Frame to hold the components 
		this.add(jlb);
		jlb.setBounds(150, 50, 200, 40);
		jlb.setForeground(Color.black);
		jlb.setFont(new Font("Dialog", 1, 25));

		this.add(jbtClient);
		jbtClient.setBounds(100, 130, 110, 50);
		jbtClient.setBackground(new Color(123, 104, 238));
		jbtClient.setForeground(Color.white);
		jbtClient.setFont(new Font("Dialog", 1, 12));
		jbtClient.addActionListener(new Listener());
		
		this.add(jbtServer);
		jbtServer.setBounds(250, 130, 110, 50);
		jbtServer.setBackground(new Color(123, 104, 238));
		jbtServer.setForeground(Color.white);
		jbtServer.setFont(new Font("Dialog", 1, 12));
		jbtServer.addActionListener(new Listener());
		
		// Frame to set properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(500, 300);
		
		// Show the frame 
		this.setVisible(true);
	}

	// Class Listener to listen button's actions
	private class Listener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// Add Client button is clicked
			if(e.getSource() == jbtClient) {
				// Create a client view
				c = new ClientView();
				
				// Create client controller
				ClientController cc = new ClientController(c,m);	
			}
			// Start Server button is clicked
			else if(e.getSource() == jbtServer) {
				try {
					// Server controller to create
					ServerController sc = new ServerController(s,m);
					// Registry server
					sc.register(sc);
				} catch (RemoteException ex) {
					// TODO Auto-generated catch block
					System.out.println(ex.getMessage());
				}
				
				// Server view to show
				s.setVisible(true);
				
			}
			
		}

	}
}



