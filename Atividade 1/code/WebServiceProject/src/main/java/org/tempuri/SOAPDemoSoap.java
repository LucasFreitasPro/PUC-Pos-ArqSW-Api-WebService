/**
 * SOAPDemoSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public interface SOAPDemoSoap extends java.rmi.Remote {
    public long addInteger(java.lang.Long arg1, java.lang.Long arg2) throws java.rmi.RemoteException;
    public long divideInteger(java.lang.Long arg1, java.lang.Long arg2) throws java.rmi.RemoteException;
    public org.tempuri.Person findPerson(java.lang.String id) throws java.rmi.RemoteException;
    public org.tempuri.DataSet getByName(java.lang.String name) throws java.rmi.RemoteException;
    public org.tempuri.ByNameDataSet getDataSetByName(java.lang.String name) throws java.rmi.RemoteException;
    public org.tempuri.PersonIdentification[] getListByName(java.lang.String name) throws java.rmi.RemoteException;
    public org.tempuri.Address lookupCity(java.lang.String zip) throws java.rmi.RemoteException;
    public java.lang.String mission() throws java.rmi.RemoteException;
    public org.tempuri.QueryByName_DataSet queryByName(java.lang.String name) throws java.rmi.RemoteException;
}
