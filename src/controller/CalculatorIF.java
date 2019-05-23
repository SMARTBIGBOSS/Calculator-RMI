package controller;
import java.rmi.Remote;
import java.rmi.RemoteException;

/*
 * Author: Anqi Li
 * Data: 3/29/2019
 * Title: Assignment3 Remote Method invocation
 * 
 * Description: The remote interface of calculator.
 */

public interface CalculatorIF extends Remote{
	public void Add(double x, double y) throws RemoteException;
	public void Subtract(double x, double y) throws RemoteException;
	public void Multiply(double x, double y) throws RemoteException;
	public void Divide(double x, double y) throws RemoteException;

}
