package org.tempuri;

public class SOAPDemoSoapProxy implements org.tempuri.SOAPDemoSoap {
  private String _endpoint = null;
  private org.tempuri.SOAPDemoSoap sOAPDemoSoap = null;
  
  public SOAPDemoSoapProxy() {
    _initSOAPDemoSoapProxy();
  }
  
  public SOAPDemoSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initSOAPDemoSoapProxy();
  }
  
  private void _initSOAPDemoSoapProxy() {
    try {
      sOAPDemoSoap = (new org.tempuri.SOAPDemoLocator()).getSOAPDemoSoap();
      if (sOAPDemoSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)sOAPDemoSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)sOAPDemoSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (sOAPDemoSoap != null)
      ((javax.xml.rpc.Stub)sOAPDemoSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.tempuri.SOAPDemoSoap getSOAPDemoSoap() {
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap;
  }
  
  public long addInteger(java.lang.Long arg1, java.lang.Long arg2) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.addInteger(arg1, arg2);
  }
  
  public long divideInteger(java.lang.Long arg1, java.lang.Long arg2) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.divideInteger(arg1, arg2);
  }
  
  public org.tempuri.Person findPerson(java.lang.String id) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.findPerson(id);
  }
  
  public org.tempuri.DataSet getByName(java.lang.String name) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.getByName(name);
  }
  
  public org.tempuri.ByNameDataSet getDataSetByName(java.lang.String name) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.getDataSetByName(name);
  }
  
  public org.tempuri.PersonIdentification[] getListByName(java.lang.String name) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.getListByName(name);
  }
  
  public org.tempuri.Address lookupCity(java.lang.String zip) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.lookupCity(zip);
  }
  
  public java.lang.String mission() throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.mission();
  }
  
  public org.tempuri.QueryByName_DataSet queryByName(java.lang.String name) throws java.rmi.RemoteException{
    if (sOAPDemoSoap == null)
      _initSOAPDemoSoapProxy();
    return sOAPDemoSoap.queryByName(name);
  }
  
  
}