package org.tallerJava.commerceModule.infrastructure.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import org.jboss.logging.Logger;
import java.util.Locale;
import org.tallerJava.commerceModule.domain.dto.ComplaintMessage;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.infrastructure.llm.LLMClient;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/ComplaintQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "maxSession", propertyValue = "1")
                                    // "maxSession" Establece el número máximo de consumidores que estarán procesando
})
public class ComplaintMessageConsumer implements MessageListener {
    private static final Logger log = Logger.getLogger(ComplaintMessageConsumer.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof ObjectMessage objectMessage) {
                ComplaintMessage complaintMessage = (ComplaintMessage) objectMessage.getObject();
                String qualification;
                try {
                    qualification = LLMClient.clasificarReclamo(complaintMessage.getMessage()).toUpperCase(Locale.ROOT);
                    System.out.println("R: " + qualification);
                } catch (Exception e) {
                    log.error("Error al clasificar con LLM", e);
                    qualification = "ERROR";
                }
                commerceRepository.createComplaint(complaintMessage.getRutCommerce(), complaintMessage.getMessage(), qualification);
            }
        } catch (JMSException e) {
            throw new RuntimeException("Error al procesar JMS", e);
        }
    }
}
