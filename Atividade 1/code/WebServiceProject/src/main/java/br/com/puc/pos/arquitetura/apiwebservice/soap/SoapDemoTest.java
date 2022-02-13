package br.com.puc.pos.arquitetura.apiwebservice.soap;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.tempuri.Person;
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

			System.out.println("Invoke divideInteger method...");
			response = soapDemoSoap.divideInteger(245784l, 2l);
			System.out.println("divideInteger method response: " + response);

			System.out.println("Invoke findPerson method...");
			Person person = soapDemoSoap.findPerson("1");
			System.out.println("findPerson method response:\n " + person);

		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
