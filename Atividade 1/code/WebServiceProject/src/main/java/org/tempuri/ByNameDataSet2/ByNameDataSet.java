/**
 * ByNameDataSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.ByNameDataSet2;

public class ByNameDataSet implements java.io.Serializable {
	private org.tempuri.ByNameDataSet2.ByNameDataSetByName byName;

	public ByNameDataSet() {
	}

	public ByNameDataSet(org.tempuri.ByNameDataSet2.ByNameDataSetByName byName) {
		this.byName = byName;
	}

	/**
	 * Gets the byName value for this ByNameDataSet.
	 * 
	 * @return byName
	 */
	public org.tempuri.ByNameDataSet2.ByNameDataSetByName getByName() {
		return byName;
	}

	/**
	 * Sets the byName value for this ByNameDataSet.
	 * 
	 * @param byName
	 */
	public void setByName(org.tempuri.ByNameDataSet2.ByNameDataSetByName byName) {
		this.byName = byName;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof ByNameDataSet))
			return false;
		ByNameDataSet other = (ByNameDataSet) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.byName == null && other.getByName() == null)
				|| (this.byName != null && this.byName.equals(other.getByName())));
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
		if (getByName() != null) {
			_hashCode += getByName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			ByNameDataSet.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/ByNameDataSet", ">ByNameDataSet"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("byName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/ByNameDataSet", "ByName"));
		elemField.setXmlType(
				new javax.xml.namespace.QName("http://tempuri.org/ByNameDataSet", ">>ByNameDataSet>ByName"));
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
