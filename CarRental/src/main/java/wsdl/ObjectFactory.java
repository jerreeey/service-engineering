
package wsdl;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wsdl package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UserCredentials_QNAME = new QName("http://tempuri.org/", "UserCredentials");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConvertCurrency }
     * 
     */
    public ConvertCurrency createConvertCurrency() {
        return new ConvertCurrency();
    }

    /**
     * Create an instance of {@link ConvertCurrencyResponse }
     * 
     */
    public ConvertCurrencyResponse createConvertCurrencyResponse() {
        return new ConvertCurrencyResponse();
    }

    /**
     * Create an instance of {@link UserCredentials }
     * 
     */
    public UserCredentials createUserCredentials() {
        return new UserCredentials();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserCredentials }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link UserCredentials }{@code >}
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "UserCredentials")
    public JAXBElement<UserCredentials> createUserCredentials(UserCredentials value) {
        return new JAXBElement<UserCredentials>(_UserCredentials_QNAME, UserCredentials.class, null, value);
    }

}
