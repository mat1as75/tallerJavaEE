package org.tallerJava.commerceModule.interfase.remote.rest;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.interfase.remote.rest.dto.CommerceDTO;

import java.util.List;

@ApplicationScoped
@Path( "/commerce")
public class CommerceAPI {
    private static final Logger log = Logger.getLogger(CommerceAPI.class);

    @Inject
    private CommerceService commerceService;



    @GET
    @Path("/{rut}")
    @Produces(MediaType.APPLICATION_JSON)
    public Commerce findByRut(@PathParam("rut") int rut) {
        log.infof("Finding Commerce with rut: %d", rut);

        return commerceService.getByRut(rut);
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Commerce> findAll() {
        log.infof("Finding all Commerces");

        return commerceService.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createCommerce(CommerceDTO commerceDTO) {
        log.infof("Creating Commerce: %s", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();
        commerceService.create(commerce.getRut(), commerce.getEmail(), commerce.getPassword(), commerce.getAccount());
    }

    @PUT
    @Produces
    public void updateCommerce(CommerceDTO commerceDTO) {
        log.infof("Updating Commerce: %s", commerceDTO);

        commerceService.update(commerceDTO.getRut(), commerceDTO.getEmail(), commerceDTO.getPassword(), commerceDTO.buildCommerce().getAccount());
    }

    @PATCH
    @Produces
    public void updateCommercePassword(CommerceDTO commerceDTO) {
        log.infof("Updating Commerce password: %s", commerceDTO);

        commerceService.changePassword(commerceDTO.getRut(), commerceDTO.getPassword());
    }

    @DELETE
    @Path("/{rut}")
    @Produces
    public void deleteCommerce(@PathParam("rut") int rut) {
        log.infof("Deleting Commerce with rut: %d", rut);

        commerceService.delete(rut);
    }




}
