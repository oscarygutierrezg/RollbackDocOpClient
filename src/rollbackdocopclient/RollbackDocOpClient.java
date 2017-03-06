/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rollbackdocopclient;

import cl.cla.osb.RequestEAI119RollbackDocumento;
import cl.cla.osb.ResponseEAI119RollbackDocumento;
import cl.cla.osb.RollbackDocOp;
import cl.cla.osb.RollbackDocOp_Service;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author oscar
 */
public class RollbackDocOpClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            RollbackDocOp_Service service = new RollbackDocOp_Service();
            RollbackDocOp port = service.getRollbackDocOpPort();

            BindingProvider bp = (BindingProvider) port;
            Map<String, Object> rc = bp.getRequestContext();
            rc.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://lasiewebsit.ccaf.andes:8080/enterprise/EntRollbackDocumentoService?WSDL");

            RequestEAI119RollbackDocumento request = new RequestEAI119RollbackDocumento();
            RequestEAI119RollbackDocumento.Header header = new RequestEAI119RollbackDocumento.Header();
            header.setCanal("SIEBEL");
            header.setFechaTransaccion(dateToxmlGregorianCalendar(new Date()));
            header.setIdTransaccion("SBL434234234");
            header.setUsuario("EJFRONT01");
            RequestEAI119RollbackDocumento.Documento.InformacionCabecera informacionCabecera = new RequestEAI119RollbackDocumento.Documento.InformacionCabecera();
            informacionCabecera.setGrupoSeguridad("Oferta");
            informacionCabecera.setNombre("WCC_007012");
            informacionCabecera.setTipo("Otros");
            RequestEAI119RollbackDocumento.Documento.Metadata metadata = new RequestEAI119RollbackDocumento.Documento.Metadata();
            metadata.setFechaVencimiento(dateToxmlGregorianCalendar(new Date()));
            RequestEAI119RollbackDocumento.Documento documento = new RequestEAI119RollbackDocumento.Documento();
            documento.setInformacionCabecera(informacionCabecera);
            documento.setMetadata(metadata);
            request.setHeader(header);
            request.setDocumento(documento);
            ResponseEAI119RollbackDocumento response = port.rollbackDocINOperation(request);
            System.out.println("response " + response);
            System.out.println("Codigo " + response.getEstado().getCodigo());
            System.out.println("Mensaje " + response.getEstado().getMensaje());
            if (response != null && response.getEstado() != null) {
                System.out.println("Codigo " + response.getEstado().getCodigo());
                System.out.println("Mensaje " + response.getEstado().getMensaje());

            } else {
                throw new Exception("Error invocando el servicio rollback documento");
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static XMLGregorianCalendar dateToxmlGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        XMLGregorianCalendar date2 = null;
        try {
            date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
        return date2;
    }

}
