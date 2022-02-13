/**
 * QueryByName_DataSetQueryByName.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.QueryByName_DataSet2;

public class QueryByName_DataSetQueryByName implements java.io.Serializable {
	private java.lang.Long ID;

	private java.lang.String name;

	private java.util.Date DOB;

	private java.lang.String SSN;

	public QueryByName_DataSetQueryByName() {
	}

	public QueryByName_DataSetQueryByName(java.lang.Long ID, java.lang.String name, java.util.Date DOB,
			java.lang.String SSN) {
		this.ID = ID;
		this.name = name;
		this.DOB = DOB;
		this.SSN = SSN;
	}

	/**
	 * Gets the ID value for this QueryByName_DataSetQueryByName.
	 * 
	 * @return ID
	 */
	public java.lang.Long getID() {
		return ID;
	}

	/**
	 * Sets the ID value for this QueryByName_DataSetQueryByName.
	 * 
	 * @param ID
	 */
	public void setID(java.lang.Long ID) {
		this.ID = ID;
	}

	/**
	 * Gets the name value for this QueryByName_DataSetQueryByName.
	 * 
	 * @return name
	 */
	public java.lang.String getName() {
		return name;
	}

	/**
	 * Sets the name value for this QueryByName_DataSetQueryByName.
	 * 
	 * @param name
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * Gets the DOB value for this QueryByName_DataSetQueryByName.
	 * 
	 * @return DOB
	 */
	public java.util.Date getDOB() {
		return DOB;
	}

	/**
	 * Sets the DOB value for this QueryByName_DataSetQueryByName.
	 * 
	 * @param DOB
	 */
	public void setDOB(java.util.Date DOB) {
		this.DOB = DOB;
	}

	/**
	 * Gets the SSN value for this QueryByName_DataSetQueryByName.
	 * 
	 * @return SSN
	 */
	public java.lang.String getSSN() {
		return SSN;
	}

	/**
	 * Sets the SSN value for this QueryByName_DataSetQueryByName.
	 * 
	 * @param SSN
	 */
	public void setSSN(java.lang.String SSN) {
		this.SSN = SSN;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof QueryByName_DataSetQueryByName))
			return false;
		QueryByName_DataSetQueryByName other = (QueryByName_DataSetQueryByName) obj;
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
				&& ((this.ID == null && other.getID() == null) || (this.ID != null && this.ID.equals(other.getID())))
				&& ((this.name == null && other.getName() == null)
						|| (this.name != null && this.name.equals(other.getName())))
				&& ((this.DOB == null && other.getDOB() == null)
						|| (this.DOB != null && this.DOB.equals(other.getDOB())))
				&& ((this.SSN == null && other.getSSN() == null)
						|| (this.SSN != null && this.SSN.equals(other.getSSN())));
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
		if (getID() != null) {
			_hashCode += getID().hashCode();
		}
		if (getName() != null) {
			_hashCode += getName().hashCode();
		}
		if (getDOB() != null) {
			_hashCode += getDOB().hashCode();
		}
		if (getSSN() != null) {
			_hashCode += getSSN().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			QueryByName_DataSetQueryByName.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet",
				">>QueryByName_DataSet>QueryByName"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("ID");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", "ID"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("name");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", "Name"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("DOB");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", "DOB"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
		elemField.setMinOccurs(0);
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("SSN");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", "SSN"));
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

}
