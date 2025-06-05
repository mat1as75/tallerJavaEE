package bancoclientemock;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebEndpoint;
import jakarta.xml.ws.WebServiceClient;
import jakarta.xml.ws.WebServiceFeature;

/**
 * This class was generated/edited to match Apache CXF/Jakarta standards.
 */
@WebServiceClient(
        name = "DepositSOAPService",
        wsdlLocation = "http://localhost:8080/BancoClienteMock-1.0-SNAPSHOT/DepositSOAP?wsdl",
        targetNamespace = "http://soap.ws.interfaces.bancoclientemock/")
public class DepositSOAPService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.ws.interfaces.bancoclientemock/", "DepositSOAPService");
    public final static QName DepositSOAPPort = new QName("http://soap.ws.interfaces.bancoclientemock/", "DepositSOAPPort");

    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8080/BancoClienteMock-1.0-SNAPSHOT/DepositSOAP?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(DepositSOAPService.class.getName())
                    .log(java.util.logging.Level.INFO,
                            "Can not initialize the default wsdl from {0}", "http://localhost:8080/BancoClienteMock-1.0-SNAPSHOT/DepositSOAP?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public DepositSOAPService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public DepositSOAPService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public DepositSOAPService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public DepositSOAPService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public DepositSOAPService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public DepositSOAPService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns DepositSOAP
     */
    @WebEndpoint(name = "DepositSOAPPort")
    public DepositSOAP getDepositSOAPPort() {
        return super.getPort(DepositSOAPPort, DepositSOAP.class);
    }

    /**
     * @param features List of features for the proxy.
     * @return returns DepositSOAP
     */
    @WebEndpoint(name = "DepositSOAPPort")
    public DepositSOAP getDepositSOAPPort(WebServiceFeature... features) {
        return super.getPort(DepositSOAPPort, DepositSOAP.class, features);
    }
}