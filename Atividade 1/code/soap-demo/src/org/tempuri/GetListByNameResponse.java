/**
 * GetListByNameResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class GetListByNameResponse  implements java.io.Serializable {
    private org.tempuri.PersonIdentification[] getListByNameResult;

    public GetListByNameResponse() {
    }

    public GetListByNameResponse(
           org.tempuri.PersonIdentification[] getListByNameResult) {
           this.getListByNameResult = getListByNameResult;
    }


    /**
     * Gets the getListByNameResult value for this GetListByNameResponse.
     * 
     * @return getListByNameResult
     */
    public org.tempuri.PersonIdentification[] getGetListByNameResult() {
        return getListByNameResult;
    }


    /**
     * Sets the getListByNameResult value for this GetListByNameResponse.
     * 
     * @param getListByNameResult
     */
    public void setGetListByNameResult(org.tempuri.PersonIdentification[] getListByNameResult) {
        this.getListByNameResult = getListByNameResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetListByNameResponse)) return false;
        GetListByNameResponse other = (GetListByNameResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.getListByNameResult==null && other.getGetListByNameResult()==null) || 
             (this.getListByNameResult!=null &&
              java.util.Arrays.equals(this.getListByNameResult, other.getGetListByNameResult())));
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
        if (getGetListByNameResult() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getGetListByNameResult());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getGetListByNameResult(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetListByNameResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", ">GetListByNameResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("getListByNameResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "GetListByNameResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "PersonIdentification"));
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org", "PersonIdentification"));
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
