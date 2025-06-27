# Documentación de la Queue ComplaintQueue

## ¿Qué es una Queue asíncrona JMS en WildFly?
Una queue asíncrona JMS (Java Message Service) es un mecanismo de mensajería que permite desacoplar la comunicación entre componentes de una aplicación. Los mensajes enviados a la queue se almacenan temporalmente y son procesados de manera asíncrona por consumidores, lo que permite distribuir la carga de trabajo, mejorar la escalabilidad y tolerar picos de tráfico sin perder mensajes. En WildFly, las queues JMS se configuran y gestionan fácilmente, integrándose con aplicaciones Java EE para soportar arquitecturas reactivas y resilientes.

## Descripción
La queue `ComplaintQueue` es utilizada para desacoplar el procesamiento de quejas en el módulo de comercio. Permite recibir mensajes de queja y procesarlos de manera asíncrona, distribuyendo la carga y mejorando la escalabilidad del sistema.

### Flujo de trabajo
1. El endpoint REST `/commerce/{rut}/makeComplaint` recibe la queja y llama a `CommerceService.createComplaint()`.
2. `CommerceServiceImpl` utiliza `ComplaintMessageProducer` para enviar el mensaje a la queue JMS.
3. `ComplaintMessageConsumer` consume el mensaje, realiza el análisis de sentimiento (simulado) y persiste la queja en la base de datos usando `CommerceRepository`.

## Implementación
- **Productor:** `ComplaintMessageProducer` utiliza `JMSContext` y la queue `java:/jms/queue/ComplaintQueue` para enviar mensajes de tipo `ComplaintMessage`.
- **Consumidor:** `ComplaintMessageConsumer` es un Message-Driven Bean que escucha la queue y procesa los mensajes recibidos.
- **Persistencia:** El repositorio `CommerceRepositoryImpl` se encarga de guardar la queja y su calificación en la base de datos.

## Pruebas de Estrés
Se realizaron pruebas de estrés enviando un alto volumen de requests (800 requests/segundo) durante  15 minutos a la aplicación , con el objetivo de evaluar el comportamiento de la queue y del sistema bajo carga.

### Resultados Observados
Durante las pruebas se detectaron los siguientes errores:

- `UT005090` y `UT005023`: Errores de Undertow relacionados con el manejo de requests HTTP.  
- `org.hibernate.exception.GenericJDBCException: Unable to acquire JDBC Connection [jakarta.resource.ResourceException: IJ000453: Unable to get managed connection for java:jboss/MariaDB]`: Indica que el pool de conexiones a la base de datos se agotó, probablemente por exceso de carga o falta de recursos.
- `java.lang.IllegalStateException: UT000131: Buffer pool is closed`: El buffer pool de Undertow se cerró inesperadamente, generalmente por un shutdown o por falta de memoria.
- `java.lang.OutOfMemoryError`: El servidor se quedó sin memoria, lo que puede ser causado por un alto volumen de mensajes. Tener en cuenta que se probó en un entorno de desarrollo con características limitadas y pocos recursos asignados al servidor.

### Análisis
- El uso de la queue permitió repartir la carga de trabajo en el tiempo, evitando que todas las requests fueran procesadas simultáneamente.
- Los graficos muestran la capacidad de la queue dado un número de requests constantes pero suficientes para hacer uso del buffer de la queue y distribuir la carga de manera asíncrona y eficiente.
- Los errores observados indican que, bajo cargas extremas, es necesario ajustar la configuración del pool de conexiones, el tamaño del buffer y la memoria asignada al servidor.

## Recomendaciones
- Monitorear el uso de memoria y considerar aumentar los recursos del servidor si se esperan picos de tráfico.
- Configurar el parámetro `maxSession` en el MDB para controlar el paralelismo de consumidores.
- Validar la configuración de la queue en el archivo `config.cli` para asegurar su correcta provisión en el servidor.

## Configuración
- La queue se crea automáticamente en el servidor WildFly/Payara mediante el script `config.cli`:

```
/subsystem=messaging-activemq/server=default/jms-queue=ComplaintQueue:add(entries=["java:/jms/queue/ComplaintQueue","java:jboss/exported/jms/queue/ComplaintQueue"])
```

- El consumidor está configurado con `maxSession=1` para su test, en entornos de producción se debería ajustar el parámetro.


# Análisis de Carga y Procesamiento Asíncrono

Se adjuntan gráficas que comparan la cantidad de **requests** enviadas, el comportamiento de la **queue** (cola de procesamiento) y el uso del **buffer** en el servidor.

Durante la prueba, se enviaron **100 requests por segundo** utilizando **JMeter** hacia el siguiente endpoint:


> ⚠️ La primera gráfica representa únicamente las **solicitudes enviadas** desde el cliente. No refleja el procesamiento interno en el servidor, lo que motivó la implementación de logs detallados en el backend para entender mejor el comportamiento real.

---

## 🟦 Solicitudes Enviadas (JMeter)

![Histograma de Time](https://github.com/user-attachments/assets/fb3b745d-f943-40cf-b46c-b49299de8628)

---

## 🟩 Eventos Registrados en el Servidor

De forma **sincrónica**, se observa el comportamiento del servidor, en donde se registran los eventos internos con timestamps equivalentes. Donde se observa que a pesar de que la carga desde la request finaliza a los 30 segundos la queue sigue procesando mensajes incluso 1 minuto más tarde de haber recibido la carga.

![Recuento de Hora](https://github.com/user-attachments/assets/c89fd400-8a71-4cc7-9207-b2c681e355ca)

---

## 🟨 Distribución del Tiempo de Respuesta

Gracias al uso de una **queue asíncrona**, el servidor es capaz de mantener una **respuesta estable** incluso ante una carga constante. Esta arquitectura desacopla la recepción de las solicitudes del procesamiento interno, evitando cuellos de botella y saturaciones inmediatas.

![Response time distribution](https://github.com/user-attachments/assets/fe67662e-31cc-4f4b-9f0e-dfc1bb60fff7)

Como puede verse, los tiempos de respuesta del servidor se mantienen dentro de márgenes aceptables, lo que evidencia que el uso de una **queue JMS** actúa como amortiguador entre el volumen de entrada y la capacidad de procesamiento del backend.


---
