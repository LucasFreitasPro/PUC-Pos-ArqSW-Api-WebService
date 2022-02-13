/**
 * GetDataSetByNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetDataSetByNameResponse  implements java.io.Serializable {
    private org.tempuri.ByNameDataSet getDataSetByNameResult;

    public GetDataSetByNameResponse() {
    }

    public GetDataSetByNameResponse(
           org.tempuri.ByNameDataSet getDataSetByNameResult) {
           this.getDataSetByNameResult = getDataSetByNameResult;
    }


    /**
     * Gets the getDataSetByNameResult value for this GetDataSetByNameResponse.
     * 
     * @return getDataSetByNameResult
     */
    public org.tempuri.ByNameDataSet getGetDataSetByNameResult() {
        return getDataSetByNameResult;
    }


    /**
     * Sets the getDataSetByNameResult value for this GetDataSetByNameResponse.
     * 
     * @param getDataSetByNameResult
     */
    public void setGetDataSetByNameResult(org.tempuri.ByNameDataSet getDataSetByNameResult) {
        this.getDataSetByNameResult = getDataSetByNameResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetDataSetByNameResponse)) return false;
        GetDataSetByNameResponse other = (GetDataSetByNameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getDataSetByNameResult==null && other.getGetDataSetByNameResult()==null) || 
             (this.getDataSetByNameResult!=null &&
              this.getDataSetByNameResult.equals(other.getGetDataSetByNameResult())));
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
        if (getGetDataSetByNameResult() != null) {
            _hashCode += getGetDataSetByNameResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetDataSetByNameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", ">GetDataSetByNameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getDataSetByNameResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "GetDataSetByNameResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "ByNameDataSet"));
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
