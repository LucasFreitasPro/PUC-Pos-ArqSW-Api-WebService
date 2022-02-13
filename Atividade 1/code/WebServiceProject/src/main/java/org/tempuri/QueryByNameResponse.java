/**
 * QueryByNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class QueryByNameResponse  implements java.io.Serializable {
    private org.tempuri.QueryByName_DataSet queryByNameResult;

    public QueryByNameResponse() {
    }

    public QueryByNameResponse(
           org.tempuri.QueryByName_DataSet queryByNameResult) {
           this.queryByNameResult = queryByNameResult;
    }


    /**
     * Gets the queryByNameResult value for this QueryByNameResponse.
     * 
     * @return queryByNameResult
     */
    public org.tempuri.QueryByName_DataSet getQueryByNameResult() {
        return queryByNameResult;
    }


    /**
     * Sets the queryByNameResult value for this QueryByNameResponse.
     * 
     * @param queryByNameResult
     */
    public void setQueryByNameResult(org.tempuri.QueryByName_DataSet queryByNameResult) {
        this.queryByNameResult = queryByNameResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryByNameResponse)) return false;
        QueryByNameResponse other = (QueryByNameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.queryByNameResult==null && other.getQueryByNameResult()==null) || 
             (this.queryByNameResult!=null &&
              this.queryByNameResult.equals(other.getQueryByNameResult())));
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
        if (getQueryByNameResult() != null) {
            _hashCode += getQueryByNameResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryByNameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", ">QueryByNameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("queryByNameResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "QueryByNameResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "QueryByName_DataSet"));
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
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
