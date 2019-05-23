package controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.JOptionPane;

import model.Model;
import view.ClientView;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method Invocation - Calculator
 * 
 * Description: The controller of client view handles submits requests.
 *              It uses the remote methods provided by server.
 */

public class ClientController {
	// Declare model to get the calculation result
	private Model model;
	
	// Declare Client view
	private ClientView clientView;
	
	// Input to hold the operands show in Text Field
	private String input = "";
	
	// Record to hold the expression
	private String record = "";
	
	// X to hold the first operand
	private double x = 0;
	
	// Y to hold the second operand
	private double y = 0;
	
	// Operator to hold the operator selected by client
	private String operator;
	
	// IsNum to identify numbers
	private boolean isNum = false;
	
	// IsOperator to identify operators
	private boolean isOperator = false;
	
	// IsClean to identify if reset button clicked
	private boolean isClean = false;
	
	// IsGetResult to identify if submit button clicked
	private boolean isGetResult = false;
	
	// IsFinish to identify if submit request finish
	private boolean isFinish = false;
	
	//The CalculatorIF object "obj" is the identifier that is
	// used to refer to the remote object that implements the Calculator interface.
	private static CalculatorIF obj;
	
	// Class constructor
	public ClientController(ClientView clientView, Model model) {
		// Connect to server and show the client view
		try {
			obj = (CalculatorIF)Naming.lookup("//localhost/Calculator");
			this.clientView = clientView;
			this.clientView.addCalculateLiserner(new CalculateListener());
			this.clientView.setVisible(true);
			this.model = model;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Client error:" + e.getMessage());
			JOptionPane.showMessageDialog(null, "Connection refused to host: localhost", "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// Function to call the remote methods provided by server
	public void invoke(double x, double y, String operator) {
//		double r = 0;
		try {
			switch(operator) {
			case "+":
				obj.Add(x, y);
				break;
			case "-":
				obj.Subtract(x, y);
				break;
			case "*":
				obj.Multiply(x, y);
				break;
			case "/":
				obj.Divide(x, y);
				break;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Client error:" + e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// Inner Class to implements action listener
	class CalculateListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			// Get the buttons' text
			String actionCommand = e.getActionCommand();

			if(record.split(" ").length > 3) {
				JOptionPane.showMessageDialog(null, "Only two operands and one operator are allowed", "Warning",JOptionPane.WARNING_MESSAGE);
			} else {
				// Identify different buttons
				if(actionCommand.equals("/") || actionCommand.equals("*") 
						|| actionCommand.equals("-") || actionCommand.equals("+")) {
					isOperator = true;
				}else if(actionCommand.equals("c")) {
					isClean = true;
				}else if(actionCommand.equals("=")) {
					isGetResult =true;
				} else {
					isNum = true;
				}
				
				// A number is inputed
				if(isNum) { 
					if(isFinish) {
						clean();
					} 
//					else {
						//The first digit is 0
						if(input.equals("0") && record.split(" ").length>1) { //first digit is 0
							JOptionPane.showMessageDialog(null, "The operand is illegal, please re-enter", "Warning",JOptionPane.WARNING_MESSAGE);
							record = record.substring(0, record.length()-1);
							System.out.println(record);
							input = "";
						}else if(input.equals("0") && record.split(" ").length==1){
							JOptionPane.showMessageDialog(null, "The operand is illegal, please recalculate", "Warning",JOptionPane.WARNING_MESSAGE);
							clean();
						} else {
							input += actionCommand;
							record += actionCommand;
						}
//					}
					isFinish = false;
					isNum = false;
				}

				// A operator is inputed
				if(isOperator) {
					if(record.isEmpty()) {
						record += actionCommand+" ";
					} else if(input.isEmpty() && record.split(" ").length==1) {
						JOptionPane.showMessageDialog(null, "Can't have two operators, please re-enter", 
								"Warning",JOptionPane.WARNING_MESSAGE);
						clean();
					} else if(!input.isEmpty() && record.split(" ").length==2){// + 1 
						JOptionPane.showMessageDialog(null, "Wrong expression", "Error",JOptionPane.ERROR_MESSAGE);
						clean();
					} else if(input.isEmpty() && record.split(" ").length==2){//1 +
						input = "";
						String temp[] = record.split(" ");
						record = temp[0];
						record += " "+actionCommand+" ";
					} else if(record.split(" ").length==3){//1 + 1
						JOptionPane.showMessageDialog(null,
								"Can only do two numbers of calculations, please calculate the result directly",
								"Warning",JOptionPane.WARNING_MESSAGE);
					} else {//1
						input = "";
						record += " "+actionCommand+" ";
					}
					isFinish = false;
					isOperator = false;
				}
				
				// Submit a request
				if(isGetResult) {
					String s[] = record.split(" ");
					if(s.length<3 && input.isEmpty()) {//+ ;1 + ;
						input = "0";
						record += " "+"Wrong expression!";
						clientView.setJta(record+"\n");
					} else if(s.length<3 && !input.isEmpty()){//1;+ 1;
						record += " "+actionCommand+" "+input;
						clientView.setJta(record+"\n");
					} else if(s[1].equals("/") && s[2].equals("0")) {
						JOptionPane.showMessageDialog(null,
								"0 cannot be divisible, please recalculate", "Error",JOptionPane.ERROR_MESSAGE);
						clean();
					} else {
						x = Double.parseDouble(s[0]);
						y = Double.parseDouble(s[2]);
						operator = s[1];
						invoke(x,y,operator);
						input = Double.toString(model.getResult());
						record += " "+actionCommand+" "+model.getResult();
						clientView.setJta(record+"\n");
					}
//					clientView.setJtf(input);
//					clientView.setJta(record+"\n");
					record = input;
					isFinish = true;
					isGetResult = false;
//					clean();
				}
				
				// Clean button is clicked
				if(isClean) {
					clean();
					isClean = false;
				}
				
				clientView.setJtf(input);
			}
			
		}
		
		// Reset the operands and operators
		public void clean() {
			input = "";
			record = "";
		}

	}
}
