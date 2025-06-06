package org.tallerJava.commerceModule.interfase.remote.rest;

import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.application.dto.CommerceDTO;
import org.tallerJava.commerceModule.application.dto.ComplaintDTO;
import org.tallerJava.commerceModule.application.dto.PosDTO;
import java.util.List;

@ApplicationScoped
@DenyAll // Por seguridad no permito ejecutar nada sin permisos de admin o user
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
    @RolesAllowed("admin")
    public Response findByRut(@PathParam("rut") long rut) {
        log.infof("Obteniendo Comercio con Rut: %d", rut);

        Commerce commerce = commerceService.getByRut(rut);
        if (commerce != null) {
            return Response.status(Response.Status.OK).entity(commerce).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + rut);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/all")
    @Transactional
    @RolesAllowed("admin")
    public Response findAll() {
        log.infof("Obteniendo todos los Comercios");
        List<Commerce> commerceList = commerceService.getAll();
        return Response.ok(commerceList).build();
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    public Response createCommerce(CommerceDTO commerceDTO) {
        log.infof("Creando Comercio: ", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();

        if (commerceService.create(commerce)) {
            return Response.status(Response.Status.CREATED).entity(commerce).build();
        } else {
            log.error("Comercio ya existente con Rut: " + commerceDTO.getRut() + " o Email: " + commerceDTO.getEmail());
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Transactional
    @RolesAllowed("user")
    public Response updateCommerce(CommerceDTO commerceDTO) {
        log.infof("Actualizando Comercio: %s", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();

        if (commerceService.update(commerce)) {
            return Response.status(Response.Status.OK).entity(commerce).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + commerceDTO.getRut());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Transactional
    @RolesAllowed("user")
    public Response updateCommercePassword(CommerceDTO commerceDTO) {
        log.infof("Actualizando Contraseña de Comercio: ", commerceDTO);

        Commerce commerce = commerceDTO.buildCommerce();

        if (commerceService.updatePassword(commerce.getRut(), commerce.getPassword())) {
            return Response.status(Response.Status.OK).entity(commerce).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + commerceDTO.getRut());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{rut}")
    @Transactional
    @RolesAllowed("admin")
    public Response deleteCommerce(@PathParam("rut") long rut) {
        log.infof("Eliminando Comercio con Rut: %d", rut);

        if (commerceService.delete(rut)) {
            return Response.status(Response.Status.OK).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + rut);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{rut}/pos")
    @Transactional
    @RolesAllowed("admin")
    public Response createPos(@PathParam("rut") long rut, PosDTO posDTO) {
        log.infof("Creando Pos con Rut: %d", rut);
        Commerce commerce = commerceService.getByRut(rut);
        if (commerceService.createPos(rut, posDTO.buildPos())) {
            return Response.ok(commerce).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + rut);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /* curl --user 123:pass1 -X POST -v http://localhost:8080/TallerJava/commerce/123/makeComplaint \
            -H "Content-Type: application/json" \
            -d '{"message": "Mensaje Enojado"}'  */
    @POST
    @Path("/{rut}/makeComplaint")
    @Transactional
    @RolesAllowed("user")
    public Response makeComplaint(@PathParam("rut") long rut, ComplaintDTO complaintDTO) {
        log.infof("Haciendo Reclamo con Rut: %d", rut);
        Commerce commerce = commerceService.getByRut(rut);
        if (commerceService.createComplaint(rut, complaintDTO.getMessage())) {
            return Response.ok(commerce).build();
        } else {
            log.error("Comercio no encontrado con Rut: " + rut);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PATCH
    @Path("/{rut}/changePosStatus")
    @Transactional
    @RolesAllowed("admin")
    public Response changePosStatus(@PathParam("rut") long rut, PosDTO posDTO) {
        log.infof("Cambiando Estado de Pos con Rut: %d", rut);
        Commerce commerce = commerceService.getByRut(rut);

        int res = commerceService.changePosStatus(rut, posDTO.buildPos(), posDTO.isStatus());
        return switch (res) {
            case 1 -> {
                log.infof("Pos con Rut: %d cambiado a estado: %b", rut, posDTO.isStatus());
                yield Response.ok(commerce).build();
            }
            case 0 -> {
                log.error("Error al cambiar estado de Pos con Rut: " + rut + " y Pos: " + posDTO.getId());
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            case -1 -> {
                log.error("Comercio no encontrado con Rut: " + rut);
                yield Response.status(Response.Status.NOT_FOUND).build();
            }
            case -2 -> {
                log.error("Pos no encontrado con Rut: " + rut + " y Pos: " + posDTO.getId());
                yield Response.status(Response.Status.NOT_FOUND).build();
            }
            default -> {
                log.error("Error desconocido al cambiar estado de Pos con Rut: " + rut + " y Pos: " + posDTO.getId());
                yield Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        };
    }

}
