package org.tallerJava.commerceModule.infrastructure.messaging;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.domain.dto.ComplaintMessage;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

@ApplicationScoped
public class ComplaintMessageProducer {
    private static final Logger log = Logger.getLogger(ComplaintMessageProducer.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/ComplaintQueue")
    private Queue complaintQueue;

    public void sendComplaintMessage(long rutCommerce, String message) {
        try {
            ComplaintMessage complaintMessage = new ComplaintMessage(rutCommerce, message);

            JMSProducer producer = context.createProducer();
            producer.send(complaintQueue, complaintMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar JMS", e);
        }
    }

}
