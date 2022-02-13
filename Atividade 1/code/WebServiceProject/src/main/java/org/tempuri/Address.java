/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Address implements java.io.Serializable {
	private java.lang.String street;

	private java.lang.String city;

	private java.lang.String state;

	private java.lang.String zip;

	public Address() {
	}

	public Address(java.lang.String street, java.lang.String city, java.lang.String state, java.lang.String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Gets the street value for this Address.
	 * 
	 * @return street
	 */
	public java.lang.String getStreet() {
		return street;
	}

	/**
	 * Sets the street value for this Address.
	 * 
	 * @param street
	 */
	public void setStreet(java.lang.String street) {
		this.street = street;
	}

	/**
	 * Gets the city value for this Address.
	 * 
	 * @return city
	 */
	public java.lang.String getCity() {
		return city;
	}

	/**
	 * Sets the city value for this Address.
	 * 
	 * @param city
	 */
	public void setCity(java.lang.String city) {
		this.city = city;
	}

	/**
	 * Gets the state value for this Address.
	 * 
	 * @return state
	 */
	public java.lang.String getState() {
		return state;
	}

	/**
	 * Sets the state value for this Address.
	 * 
	 * @param state
	 */
	public void setState(java.lang.String state) {
		this.state = state;
	}

	/**
	 * Gets the zip value for this Address.
	 * 
	 * @return zip
	 */
	public java.lang.String getZip() {
		return zip;
	}

	/**
	 * Sets the zip value for this Address.
	 * 
	 * @param zip
	 */
	public void setZip(java.lang.String zip) {
		this.zip = zip;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof Address))
			return false;
		Address other = (Address) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true
				&& ((this.street == null && other.getStreet() == null)
						|| (this.street != null && this.street.equals(other.getStreet())))
				&& ((this.city == null && other.getCity() == null)
						|| (this.city != null && this.city.equals(other.getCity())))
				&& ((this.state == null && other.getState() == null)
						|| (this.state != null && this.state.equals(other.getState())))
				&& ((this.zip == null && other.getZip() == null)
						|| (this.zip != null && this.zip.equals(other.getZip())));
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = 1;
		if (getStreet() != null) {
			_hashCode += getStreet().hashCode();
		}
		if (getCity() != null) {
			_hashCode += getCity().hashCode();
		}
		if (getState() != null) {
			_hashCode += getState().hashCode();
		}
		if (getZip() != null) {
			_hashCode += getZip().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			Address.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Address"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("street");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Street"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("city");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "City"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("state");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "State"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("zip");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Zip"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(java.lang.String mechType,
			java.lang.Class _javaType, javax.xml.namespace.QName _xmlType) {
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state=" + state + "]";
	}

}
