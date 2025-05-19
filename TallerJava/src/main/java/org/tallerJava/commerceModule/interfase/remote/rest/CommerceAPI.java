package org.tallerJava.commerceModule.interfase.remote.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.interfase.remote.rest.dto.CommerceDTO;
import org.tallerJava.commerceModule.interfase.remote.rest.dto.PosDTO;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Path( "/commerce")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CommerceAPI {
    private static final Logger log = Logger.getLogger(CommerceAPI.class);

    @Inject
    private CommerceService commerceService;

    @GET
    @Path("/{rut}")
    @Transactional
    public Response findByRut(@PathParam("rut") int rut) {
        log.infof("Finding Commerce with rut: %d", rut);

        Commerce commerce = commerceService.getByRut(rut);
        return Response.ok(commerce).build();
    }

    @GET
    @Path("/all")
    @Transactional
    public Response findAll() {
        log.infof("Finding all Commerces");
        List<Commerce> commerceList = commerceService.getAll();
        return Response.ok(commerceList).build();
    }

    @POST
    @Transactional
    public Response createCommerce(CommerceDTO commerceDTO) {
        log.infof("Creating Commerce: %s", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();
        commerceService.create(commerce);

        return Response.ok(commerce).build();
    }

    @PUT
    @Transactional
    public Response updateCommerce(CommerceDTO commerceDTO) {
        log.infof("Updating Commerce: %s", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();
        commerceService.update(commerce);

        return Response.ok(commerce).build();
    }

    @PATCH
    @Transactional
    public Response updateCommercePassword(CommerceDTO commerceDTO) {
        log.infof("Updating Commerce password: %s", commerceDTO);

        Commerce commerce = new Commerce();
        commerce.setRut(commerceDTO.getRut());
        commerce.setPassword(commerceDTO.getPassword());
        commerceService.changePassword(commerce.getRut(), commerce.getPassword());

        return Response.ok(commerce).build();
    }

    @DELETE
    @Path("/{rut}")
    @Transactional
    public Response deleteCommerce(@PathParam("rut") int rut) {
        log.infof("Deleting Commerce with rut: %d", rut);

        commerceService.delete(rut);
        return Response.ok().build();
    }




}
