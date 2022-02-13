/**
 * LookupCityResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class LookupCityResponse  implements java.io.Serializable {
    private org.tempuri.Address lookupCityResult;

    public LookupCityResponse() {
    }

    public LookupCityResponse(
           org.tempuri.Address lookupCityResult) {
           this.lookupCityResult = lookupCityResult;
    }


    /**
     * Gets the lookupCityResult value for this LookupCityResponse.
     * 
     * @return lookupCityResult
     */
    public org.tempuri.Address getLookupCityResult() {
        return lookupCityResult;
    }


    /**
     * Sets the lookupCityResult value for this LookupCityResponse.
     * 
     * @param lookupCityResult
     */
    public void setLookupCityResult(org.tempuri.Address lookupCityResult) {
        this.lookupCityResult = lookupCityResult;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LookupCityResponse)) return false;
        LookupCityResponse other = (LookupCityResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.lookupCityResult==null && other.getLookupCityResult()==null) || 
             (this.lookupCityResult!=null &&
              this.lookupCityResult.equals(other.getLookupCityResult())));
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
        if (getLookupCityResult() != null) {
            _hashCode += getLookupCityResult().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LookupCityResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", ">LookupCityResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lookupCityResult");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "LookupCityResult"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Address"));
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
