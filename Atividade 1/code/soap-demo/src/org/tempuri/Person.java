/**
 * Person.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.tempuri;

public class Person  implements java.io.Serializable {
    private java.lang.String name;

    private java.lang.String SSN;

    private java.util.Date DOB;

    private org.tempuri.Address home;

    private org.tempuri.Address office;

    private org.tempuri.Person spouse;

    private java.lang.String[] favoriteColors;

    private java.lang.Long age;

    public Person() {
    }

    public Person(
           java.lang.String name,
           java.lang.String SSN,
           java.util.Date DOB,
           org.tempuri.Address home,
           org.tempuri.Address office,
           org.tempuri.Person spouse,
           java.lang.String[] favoriteColors,
           java.lang.Long age) {
           this.name = name;
           this.SSN = SSN;
           this.DOB = DOB;
           this.home = home;
           this.office = office;
           this.spouse = spouse;
           this.favoriteColors = favoriteColors;
           this.age = age;
    }


    /**
     * Gets the name value for this Person.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Person.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the SSN value for this Person.
     * 
     * @return SSN
     */
    public java.lang.String getSSN() {
        return SSN;
    }


    /**
     * Sets the SSN value for this Person.
     * 
     * @param SSN
     */
    public void setSSN(java.lang.String SSN) {
        this.SSN = SSN;
    }


    /**
     * Gets the DOB value for this Person.
     * 
     * @return DOB
     */
    public java.util.Date getDOB() {
        return DOB;
    }


    /**
     * Sets the DOB value for this Person.
     * 
     * @param DOB
     */
    public void setDOB(java.util.Date DOB) {
        this.DOB = DOB;
    }


    /**
     * Gets the home value for this Person.
     * 
     * @return home
     */
    public org.tempuri.Address getHome() {
        return home;
    }


    /**
     * Sets the home value for this Person.
     * 
     * @param home
     */
    public void setHome(org.tempuri.Address home) {
        this.home = home;
    }


    /**
     * Gets the office value for this Person.
     * 
     * @return office
     */
    public org.tempuri.Address getOffice() {
        return office;
    }


    /**
     * Sets the office value for this Person.
     * 
     * @param office
     */
    public void setOffice(org.tempuri.Address office) {
        this.office = office;
    }


    /**
     * Gets the spouse value for this Person.
     * 
     * @return spouse
     */
    public org.tempuri.Person getSpouse() {
        return spouse;
    }


    /**
     * Sets the spouse value for this Person.
     * 
     * @param spouse
     */
    public void setSpouse(org.tempuri.Person spouse) {
        this.spouse = spouse;
    }


    /**
     * Gets the favoriteColors value for this Person.
     * 
     * @return favoriteColors
     */
    public java.lang.String[] getFavoriteColors() {
        return favoriteColors;
    }


    /**
     * Sets the favoriteColors value for this Person.
     * 
     * @param favoriteColors
     */
    public void setFavoriteColors(java.lang.String[] favoriteColors) {
        this.favoriteColors = favoriteColors;
    }


    /**
     * Gets the age value for this Person.
     * 
     * @return age
     */
    public java.lang.Long getAge() {
        return age;
    }


    /**
     * Sets the age value for this Person.
     * 
     * @param age
     */
    public void setAge(java.lang.Long age) {
        this.age = age;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Person)) return false;
        Person other = (Person) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.SSN==null && other.getSSN()==null) || 
             (this.SSN!=null &&
              this.SSN.equals(other.getSSN()))) &&
            ((this.DOB==null && other.getDOB()==null) || 
             (this.DOB!=null &&
              this.DOB.equals(other.getDOB()))) &&
            ((this.home==null && other.getHome()==null) || 
             (this.home!=null &&
              this.home.equals(other.getHome()))) &&
            ((this.office==null && other.getOffice()==null) || 
             (this.office!=null &&
              this.office.equals(other.getOffice()))) &&
            ((this.spouse==null && other.getSpouse()==null) || 
             (this.spouse!=null &&
              this.spouse.equals(other.getSpouse()))) &&
            ((this.favoriteColors==null && other.getFavoriteColors()==null) || 
             (this.favoriteColors!=null &&
              java.util.Arrays.equals(this.favoriteColors, other.getFavoriteColors()))) &&
            ((this.age==null && other.getAge()==null) || 
             (this.age!=null &&
              this.age.equals(other.getAge())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSSN() != null) {
            _hashCode += getSSN().hashCode();
        }
        if (getDOB() != null) {
            _hashCode += getDOB().hashCode();
        }
        if (getHome() != null) {
            _hashCode += getHome().hashCode();
        }
        if (getOffice() != null) {
            _hashCode += getOffice().hashCode();
        }
        if (getSpouse() != null) {
            _hashCode += getSpouse().hashCode();
        }
        if (getFavoriteColors() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getFavoriteColors());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getFavoriteColors(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getAge() != null) {
            _hashCode += getAge().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Person.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Person"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "SSN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DOB");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "DOB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "date"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("home");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Home"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("office");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Office"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Address"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spouse");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Spouse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://tempuri.org", "Person"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("favoriteColors");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "FavoriteColors"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tempuri.org", "FavoriteColorsItem"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("age");
        elemField.setXmlName(new javax.xml.namespace.QName("http://tempuri.org", "Age"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
