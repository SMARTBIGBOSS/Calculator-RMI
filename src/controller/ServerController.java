package controller;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import model.Model;
import view.ServerView;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method Invocation - Calculator
 * 
 * Description: The server controller implements RMI.
 */

public class ServerController extends UnicastRemoteObject implements CalculatorIF{
	// Declare model to set the calculation result
	private Model model;
	
	// Declare Server view
	private ServerView serverView;
	
	// ServerIp to hold the server host address
	private String serverIP;
	
	// ClientIP to hold the client host address
	private String clientIP;
	
	// Class constructor
	public ServerController(ServerView serverView, Model model) throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
		this.serverView = serverView;
		this.model = model;
	}

	// Registry a server
	public void register(ServerController sc) {
		try {
			// Bind this object instance to the name "Calculator".
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("Calculator", sc);
			serverView.setJta("Server is ready.\n");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Server warning: " + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Warning",JOptionPane.WARNING_MESSAGE);
		} 
	}

	// Implement the Add Method of RMI interface
	@Override
	public void Add(double x, double y) throws RemoteException {
		// TODO Auto-generated method stub
		double temp = x+y;
		model.setResult(temp);
		try {
			serverIP = InetAddress.getLocalHost().getHostAddress();
			try {
				clientIP = getClientHost();
			} catch (ServerNotActiveException e) {
				// TODO Auto-generated catch block
				System.out.println("Client Host Error"+e.getMessage());
			}
			serverView.setJta("Client-"+clientIP+ " connected at IP: "+serverIP+"(server host)\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Add Method Error: "+e.getMessage());
		}
		serverView.setJta("Request from Client:\n"+"Opnd1: "+x+"\nOpnd2: "+y
				+"\nOpetr: +\nData to Client-"+clientIP+": "+temp+"\n");
	}

	// Implement Subtract Method of RMI interface
	@Override
	public void Subtract(double x, double y) throws RemoteException {
		// TODO Auto-generated method stub
		double temp = x-y;
		model.setResult(temp);
		try {
			serverIP = InetAddress.getLocalHost().getHostAddress();
			try {
				clientIP = getClientHost();
			} catch (ServerNotActiveException e) {
				// TODO Auto-generated catch block
				System.out.println("Client Host Error"+e.getMessage());
			}
			serverView.setJta("Client-"+clientIP+ " connected at IP: "+serverIP+"(server host)\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Subtract Method Error: "+e.getMessage());
		}
		serverView.setJta("Request from Client:\n"+"Opnd1: "+x+"\nOpnd2: "+y
				+"\nOpetr: -\nData to Client-"+clientIP+": "+temp+"\n");
	}

	// Implement Multiply Method of RMI interface
	@Override
	public void Multiply(double x, double y) throws RemoteException {
		// TODO Auto-generated method stub
		double temp = x*y;
		model.setResult(temp);
		try {
			serverIP = InetAddress.getLocalHost().getHostAddress();
			try {
				clientIP = getClientHost();
			} catch (ServerNotActiveException e) {
				// TODO Auto-generated catch block
				System.out.println("Client Host Error"+e.getMessage());
			}
			serverView.setJta("Client-"+clientIP+ " connected at IP: "+serverIP+"(server host)\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Subtract Method Error: "+e.getMessage());
		}
		serverView.setJta("Request from Client:\n"+"Opnd1: "+x+"\nOpnd2: "+y
				+"\nOpetr: *\nData to Client-"+clientIP+": "+temp+"\n");
	}

	// implement Divide Method of RMI interface
	@Override
	public void Divide(double x, double y) throws RemoteException {
		// TODO Auto-generated method stub
		double temp = x/y;
		model.setResult(temp);
		try {
			serverIP = InetAddress.getLocalHost().getHostAddress();
			try {
				clientIP = getClientHost();
			} catch (ServerNotActiveException e) {
				// TODO Auto-generated catch block
				System.out.println("Client Host Error"+e.getMessage());
			}
			serverView.setJta("Client-"+clientIP+ " connected at IP: "+serverIP+"(server host)\n");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.out.println("Subtract Method Error: "+e.getMessage());
		}
		serverView.setJta("Request from Client:\n"+"Opnd1: "+x+"\nOpnd2: "+y
				+"\nOpetr: /\nData to Client-"+clientIP+": "+temp+"\n");
	}

}
