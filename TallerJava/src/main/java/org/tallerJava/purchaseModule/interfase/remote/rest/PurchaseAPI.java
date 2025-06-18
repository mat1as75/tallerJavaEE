package org.tallerJava.purchaseModule.interfase.remote.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.authSharedModule.AuthStatus;
import org.tallerJava.purchaseModule.application.PurchaseService;
import org.tallerJava.purchaseModule.application.dto.PaymentDataDTO;
import org.tallerJava.purchaseModule.application.dto.SalesSummaryDTO;
import org.tallerJava.purchaseModule.interfase.remote.rest.rateLimiter.RateLimiter;

@ApplicationScoped
@Path("/purchase")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PurchaseAPI {
    private static final Logger log = Logger.getLogger(PurchaseAPI.class);

    @Inject
    private PurchaseService purchaseService;

    @POST
    @RateLimiter(maxRequestsPerMinute = 300)
    @Transactional
    public Response processPayment(PaymentDataDTO paymentData) {
        AuthStatus authStatus = purchaseService.isCommerceAuthorized(paymentData.getCommerceRut(), paymentData.getPassword());
        System.out.println("STATUS PROCESS PAYMENT: %s" + authStatus);
        if (authStatus.equals(AuthStatus.NOT_AUTH)) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("No se pudo autenticar el comercio")
                    .build();
        }
        if (authStatus.equals(AuthStatus.NOT_EXISTS)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("El comercio no existe")
                    .build();
        }

        try {
            purchaseService.processPayment(paymentData);
            return Response.ok("Pago procesado exitosamente").build();
        }catch(Exception e){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No se pudo procesar el pago: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/resumenVentasDiarias/{rut}")
    public Response getDailySalesSummary(@PathParam("rut") long rut) {
        log.infof("Obteniendo resumen de ventas diarias para comercio con RUT: {}", rut);
        try {
            SalesSummaryDTO dtoResumen = purchaseService.getSalesSummaryOfTheDay(rut);
            return Response.ok(dtoResumen).build();
        } catch(Exception e) {
            log.error("Error al obtener el resumen de ventas diarias", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener información de ventas: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/resumenVentasPorPeriodo/{rut}")
    public Response getSalesSummaryByPeriod(
            @PathParam("rut") long rut,
            @QueryParam("fechaInicio") String fechaInicio,
            @QueryParam("fechaFin") String fechaFin
    ) {
        log.infof("Obteniendo resumen de ventas para RUT %d entre %s y %s", rut, fechaInicio, fechaFin);
        try {
            SalesSummaryDTO resumen = purchaseService.getSalesSummaryByPeriod(rut, fechaInicio, fechaFin);
            return Response.ok(resumen).build();
        } catch(Exception e) {
            log.error("Error al obtener el resumen por periodo", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener el resumen: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/totalVentas/{rut}")
    public Response getTotalSales(@PathParam("rut") long rut) {
        log.infof("Obteniendo el monto total de ventas para comercio con RUT: {}", rut);
        try {
            double totalAmount = purchaseService.getTotalSalesAmount(rut);

            JsonObject jsonResponse = Json.createObjectBuilder()
                    .add("rut", rut)
                    .add("montoTotal", totalAmount)
                    .build();

            return Response.ok(jsonResponse).build();
        } catch (Exception e) {
            log.error("Error al obtener el monto total de ventas", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener el monto total: " + e.getMessage())
                    .build();
        }
    }
}