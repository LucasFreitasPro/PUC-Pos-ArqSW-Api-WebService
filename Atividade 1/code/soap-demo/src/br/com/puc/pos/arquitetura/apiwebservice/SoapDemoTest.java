package br.com.puc.pos.arquitetura.apiwebservice;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.Person;
import org.tempuri.QueryByName_DataSet;
import org.tempuri.SOAPDemoLocator;
import org.tempuri.SOAPDemoSoap;

public class SoapDemoTest {

	public static void main(String[] args) {

		try {
			SOAPDemoLocator locator = new SOAPDemoLocator();
			SOAPDemoSoap soapDemoSoap = locator.getSOAPDemoSoap();

			System.out.println("Invoke addInteger method...");
			long response = soapDemoSoap.addInteger(45l, 5466l);
			System.out.println("addInteger method response: " + response);

			System.out.println("Invoke findPerson method...");
			Person person = soapDemoSoap.findPerson("123456");
			System.out.println("findPerson method response: " + person);

			System.out.println("Invoke queryByName method...");
			QueryByName_DataSet queryByName = soapDemoSoap.queryByName("Michael");
			System.out.println("queryByName method response: " + queryByName);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
