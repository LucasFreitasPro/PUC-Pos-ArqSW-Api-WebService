/**
 * QueryByName_DataSet.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri.QueryByName_DataSet2;

public class QueryByName_DataSet implements java.io.Serializable {
	private org.tempuri.QueryByName_DataSet2.QueryByName_DataSetQueryByName queryByName;

	public QueryByName_DataSet() {
	}

	public QueryByName_DataSet(org.tempuri.QueryByName_DataSet2.QueryByName_DataSetQueryByName queryByName) {
		this.queryByName = queryByName;
	}

	/**
	 * Gets the queryByName value for this QueryByName_DataSet.
	 * 
	 * @return queryByName
	 */
	public org.tempuri.QueryByName_DataSet2.QueryByName_DataSetQueryByName getQueryByName() {
		return queryByName;
	}

	/**
	 * Sets the queryByName value for this QueryByName_DataSet.
	 * 
	 * @param queryByName
	 */
	public void setQueryByName(org.tempuri.QueryByName_DataSet2.QueryByName_DataSetQueryByName queryByName) {
		this.queryByName = queryByName;
	}

	private java.lang.Object __equalsCalc = null;

	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof QueryByName_DataSet))
			return false;
		QueryByName_DataSet other = (QueryByName_DataSet) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && ((this.queryByName == null && other.getQueryByName() == null)
				|| (this.queryByName != null && this.queryByName.equals(other.getQueryByName())));
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
		if (getQueryByName() != null) {
			_hashCode += getQueryByName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(
			QueryByName_DataSet.class, true);

	static {
		typeDesc.setXmlType(
				new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", ">QueryByName_DataSet"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("queryByName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet", "QueryByName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org/QueryByName_DataSet",
				">>QueryByName_DataSet>QueryByName"));
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
