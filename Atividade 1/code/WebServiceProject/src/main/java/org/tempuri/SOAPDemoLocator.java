/**
 * SOAPDemoLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class SOAPDemoLocator extends org.apache.axis.client.Service implements org.tempuri.SOAPDemo {

    public SOAPDemoLocator() {
    }


    public SOAPDemoLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SOAPDemoLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SOAPDemoSoap
    private java.lang.String SOAPDemoSoap_address = "http://www.crcind.com/csp/samples/SOAP.Demo.cls";

    public java.lang.String getSOAPDemoSoapAddress() {
        return SOAPDemoSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SOAPDemoSoapWSDDServiceName = "SOAPDemoSoap";

    public java.lang.String getSOAPDemoSoapWSDDServiceName() {
        return SOAPDemoSoapWSDDServiceName;
    }

    public void setSOAPDemoSoapWSDDServiceName(java.lang.String name) {
        SOAPDemoSoapWSDDServiceName = name;
    }

    public org.tempuri.SOAPDemoSoap getSOAPDemoSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SOAPDemoSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSOAPDemoSoap(endpoint);
    }

    public org.tempuri.SOAPDemoSoap getSOAPDemoSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.tempuri.SOAPDemoSoapStub _stub = new org.tempuri.SOAPDemoSoapStub(portAddress, this);
            _stub.setPortName(getSOAPDemoSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSOAPDemoSoapEndpointAddress(java.lang.String address) {
        SOAPDemoSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.tempuri.SOAPDemoSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                org.tempuri.SOAPDemoSoapStub _stub = new org.tempuri.SOAPDemoSoapStub(new java.net.URL(SOAPDemoSoap_address), this);
                _stub.setPortName(getSOAPDemoSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SOAPDemoSoap".equals(inputPortName)) {
            return getSOAPDemoSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org", "SOAPDemo");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org", "SOAPDemoSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SOAPDemoSoap".equals(portName)) {
            setSOAPDemoSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
