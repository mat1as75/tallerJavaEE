package org.tallerJava.transferModule.interfase.remote.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.transferModule.application.TransferService;
import org.tallerJava.transferModule.application.dto.DateRangeDTO;
import org.tallerJava.transferModule.application.dto.DepositDTO;
import java.util.List;

@ApplicationScoped
@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferAPI {
    private static final Logger log = Logger.getLogger(TransferAPI.class);

    @Inject
    private TransferService transferService;


    // Receive Transfer Notification From Payment Method
//    @POST
//    @Path("/notification")
//    @Transactional
//    public Response receiveTransferNotification() {
//        log.infof("Recibiendo notificación desde Medio de Pago");
//
//    }

    @GET
    @Path("/deposits/{commerceRut}")
    public Response checkDeposits(@PathParam("commerceRut") long commerceRut, DateRangeDTO range) {
        log.infof("Obteniendo depósitos para el comercio con rut %d entre %s y %s", commerceRut, String.valueOf(range.getStart()), String.valueOf(range.getEnd()));
        try {
            List<DepositDTO> dtoDeposits = transferService.getDepositsSummaryByPeriod(commerceRut, range.buildDateRange());
            return Response.ok(dtoDeposits).build();
        } catch (Exception e) {
            log.error("Error al obtener los depósitos para el comercio con rut " + commerceRut, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
