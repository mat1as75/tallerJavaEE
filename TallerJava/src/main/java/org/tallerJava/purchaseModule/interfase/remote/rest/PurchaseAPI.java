package org.tallerJava.purchaseModule.interfase.remote.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.purchaseModule.application.PurchaseService;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.PaymentDataDTO;

@ApplicationScoped
@Path("/purchase")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseAPI {
    private static final Logger log = Logger.getLogger(PurchaseAPI.class);

    @Inject
    private PurchaseService purchaseService;

    @POST
    @Transactional
    public Response processPayment(PaymentDataDTO paymentData) {
        Purchase purchase = PaymentDataDTO.buildPurchase(paymentData);
        Card card = PaymentDataDTO.buildCard(paymentData.getCardData());
        int id_commerce = paymentData.getCommerceRut();
        int id_pos = paymentData.getPosId();
        try {
            purchaseService.processPayment(purchase, card, id_commerce,id_pos);
            return Response.ok("Pago procesado exitosamente").build();
        }catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se pudo procesar el pago: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/resumenVentasDiarias/{rut}")
    public Response getDailySalesSummary(@PathParam("rut") String rut) {
        log.infof("Obteniendo resumen de ventas diarias para comercio con RUT: {}", rut);
        try {
            //DailySalesSummaryDTO resumenVentas = purchaseService.getDailySalesSummary(rut);
            return Response.ok().build();
        } catch(Exception e) {
            log.error("Error al obtener el resumen de ventas diarias", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener información de ventas: " + e.getMessage())
                    .build();
        }
    }
//
//    @GET
//    @Path("/summary")
//    public Response getSalesSummaryByPeriod(
//            @QueryParam("commerceId") String commerceId,
//            @QueryParam("startDate") String startDate,
//            @QueryParam("endDate") String endDate) {
//
//        log.infof("Getting sales summary for commerce: {} from {} to {}",
//                commerceId, startDate, endDate);
//
//        return Response.ok()
//                .entity("{\"totalSales\":15000.00,\"transactionCount\":250}")
//                .build();
//    }
//
//    @GET
//    @Path("/current-sales/{commerceId}")
//    public Response getCurrentSales(@PathParam("commerceId") String commerceId) {
//        log.infof("Getting current sales for commerce: {}", commerceId);
//        return Response.ok()
//                .entity("{\"amount\":2500.00,\"lastUpdate\":\"2024-02-14T15:30:00Z\"}")
//                .build();
//    }
}