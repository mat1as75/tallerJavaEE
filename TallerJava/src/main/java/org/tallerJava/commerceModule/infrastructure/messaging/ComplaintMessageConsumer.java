package org.tallerJava.commerceModule.infrastructure.messaging;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.ObjectMessage;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.domain.dto.ComplaintMessage;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

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
                // Acá deberia de ir la consulta al endpoint del LLM
                String sentiment = "POSITIVO"; //LLM RESPONSE. Uppercase sensitive

                commerceRepository.createComplaint(complaintMessage.getRutCommerce(), complaintMessage.getMessage(), sentiment);
            }
        } catch (JMSException e) {
            throw new RuntimeException("Error al procesar JMS", e);
        }
    }
}
