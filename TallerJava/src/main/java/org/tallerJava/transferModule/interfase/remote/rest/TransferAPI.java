package org.tallerJava.transferModule.interfase.remote.rest;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.transferModule.application.TransferService;
import org.tallerJava.transferModule.application.dto.DepositDTO;
import org.tallerJava.transferModule.application.dto.TransferDetailsDTO;
import org.tallerJava.transferModule.domain.DateRange;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
@Path("/transfer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransferAPI {
    private static final Logger log = Logger.getLogger(TransferAPI.class);

    @Inject
    private TransferService transferService;

    /*
        curl -X POST -v http://localhost:8080/TallerJava/transfer/notification \
             -H "Content-Type: application/json" \
             -d '{"commerceRut": 123, "amount": 1500.00, "accountNumber": "1"}'
    */
    // Receive Transfer Notification From Payment Method
    @POST
    @Path("/notification")
    @Transactional
    public Response receiveTransferNotification(TransferDetailsDTO transferDetails) {
        log.infof("Recibiendo notificación desde Medio de Pago");

        try {
            transferService.transferDeposit(transferDetails.getCommerceRut(), transferDetails.getAccountNumber(), transferDetails.getAmount());
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar transferencia", e);
        }
    }

    /*
        curl -v curl "http://localhost:8080/TallerJava/transfer/deposits/123?start=2025-05-20&end=2025-05-25"
    */
    // Get Deposits Summary By Period
    @GET
    @Path("/deposits/{commerceRut}")
    public Response checkDeposits(
            @PathParam("commerceRut") long commerceRut,
            @QueryParam("start") String start,
            @QueryParam("end") String end
    ) {
        // Params validate
        if (isValidDateRange(start, end)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parámetros 'start' y 'end' son requeridos")
                    .build();
        }

        String startStr = normalizeDate(start);
        String endStr = normalizeDate(end);

        // Normalized params validate
        if (isValidDateRange(startStr, endStr)) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Parámetros 'start' y 'end' son inválidos")
                    .build();
        }

        LocalDate startDate = LocalDate.parse(startStr);
        LocalDate endDate = LocalDate.parse(endStr);
        DateRange range = new DateRange(startDate, endDate);

        log.infof("Obteniendo depósitos para el comercio con rut %d entre %s y %s", commerceRut, String.valueOf(range.getStart()), String.valueOf(range.getEnd()));
        try {
            List<DepositDTO> dtoDeposits = transferService.getDepositsSummaryByPeriod(commerceRut, range);
            return Response.ok(dtoDeposits).build();
        } catch (Exception e) {
            log.error("Error al obtener los depósitos para el comercio con rut " + commerceRut, e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    private boolean isValidDateRange(String start, String end) {
        // Params validate
        return start == null || end == null;
    }

    private String normalizeDate(String date) {
        String[] parts = date.split("-");
        if (parts.length != 3) {
            return null;
        }

        String year = parts[0];
        String month = String.format("%02d", Integer.parseInt(parts[1]));
        String day = String.format("%02d", Integer.parseInt(parts[2]));

        return year + "-" + month + "-" + day;
    }
}
