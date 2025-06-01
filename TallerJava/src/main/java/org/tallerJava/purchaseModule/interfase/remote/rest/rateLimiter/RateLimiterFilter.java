package org.tallerJava.purchaseModule.interfase.remote.rest.rateLimiter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

@Provider
@RateLimiter
@Priority(Priorities.AUTHORIZATION)
@Singleton
public class RateLimiterFilter implements ContainerRequestFilter {

    private static final Map<String, Bucket> BUCKET_MAP = new ConcurrentHashMap<>();

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Method method = resourceInfo.getResourceMethod(); //Obtiene el method que está siendo invocado
        String key = method.getDeclaringClass().getName() + "#" + method.getName(); //Debemos hacer esto ya que el filtro aplica a todos los endpoints, esta es la clave que identifica un endpoint de otro

        int maxRequestsPerMinute = max_request();

        // Obtener o crear un bucket para este endpoint
        Bucket bucket = BUCKET_MAP.computeIfAbsent(key, k -> createBucket(maxRequestsPerMinute));

        // Intentar consumir un token del bucket
        if (!bucket.tryConsume(1)) {
            // Si no hay tokens disponibles, rechazar la solicitud
            requestContext.abortWith(
                Response.status(Response.Status.TOO_MANY_REQUESTS)
                    .entity("Demasiadas solicitudes. Intenta más tarde.")
                    .build()
            );
        }
    }

    private Bucket createBucket(int maxRequestsPerMinute) {
        // Creamos un bucket con capacidad máxima igual a maxRequestsPerMinute
        // y configuramos que se recargue con esa misma cantidad cada minuto
        Bandwidth bucketConf = Bandwidth.builder()
                .capacity(maxRequestsPerMinute)
                .refillGreedy(maxRequestsPerMinute, Duration.ofMinutes(1)) // Tambien hay opcion refill intervally (distribuye la recarga de los tokens)
                .build();

        // Construimos y devolvemos el bucket
        return Bucket.builder().addLimit(bucketConf).build();
    }

    private int max_request() { // busca la anotacion a nivel de clase y de metodos
        Method method = resourceInfo.getResourceMethod();
        RateLimiter limiter = method.getAnnotation(RateLimiter.class);
        if (limiter == null) {
            limiter = resourceInfo.getResourceClass().getAnnotation(RateLimiter.class);
        }
        return (limiter != null) ? limiter.maxRequestsPerMinute() : 5;
    }
}
