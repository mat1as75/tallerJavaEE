# 🧾 Pasarela de Pagos - Proyecto Java con Jakarta EE 10

Este repositorio contiene el desarrollo de una **Pasarela de Pagos** modular, en el marco del Taller de Sistemas de Información de UTEC. El sistema se basa en una arquitectura por módulos diseñada para facilitar la escalabilidad y el desacoplamiento.

## 🛠️ Tecnologías

- **Jakarta EE 10** (CDI, REST, JPA, Interceptors, Messaging, Security)
- **Java SE 21**
- **WildFly** como servidor de aplicaciones
- **Docker** para contenedores
- **Grafana + InfluxDB** (observabilidad en futuras etapas)
- **Bucket4j** para manejo de límites de tasa (rate limiting)

---

## 📚 Descripción del Proyecto

Una **Pasarela de Pagos** es un intermediario entre clientes, comercios y entidades financieras que autoriza, gestiona y asegura pagos electrónicos. El sistema centraliza el proceso para múltiples medios de pago, simplificando la operación del comercio.

### Funcionalidades Clave

- Registro y administración de comercios
- Procesamiento de pagos desde POS
- Consulta de informes de ventas
- Transferencia de fondos al banco del comercio
- Monitoreo de eventos y reclamos

---

## 🗂️ Arquitectura General

El sistema se compone de varios **subsistemas** que se comunican a través de interfaces definidas:

![diagrama_p8_img1](https://github.com/user-attachments/assets/a6f87bac-ba3f-4001-8496-d52009a1e129)

## 🔧 Módulos del Core (Pasarela de Pagos)

Cada módulo implementa una funcionalidad específica del negocio y está diseñado para evolucionar como microservicio independiente:

- **Comercio**: Alta/modificación de comercios, POS, reclamos.
- **Compras**: Procesamiento de pagos, generación de reportes.
- **Transferencias**: Manejo de notificaciones y depósitos.
- **Monitoreo**: Gestión de eventos relevantes del sistema.

![diagrama_p10_img1](https://github.com/user-attachments/assets/e0a576ba-9150-4eaf-8106-1190964acb2a)

## 📌 Requerimientos Funcionales

- Registrar comercios con datos y cuenta bancaria
- Procesar pagos desde POS
- Consultar reportes diarios y por período
- Gestionar depósitos y comisiones
- Soportar reclamos de comercios

## 🔒 Requerimientos No Funcionales

- Reportes asincrónicos para evitar sobrecarga
- Almacenamiento eficiente de montos vendidos
- Seguridad: hash de contraseñas, no guardar datos de tarjeta
- Rate Limiting con aumento de comisión por abuso

---

## 🧱 Estructura de Proyecto

Cada módulo sigue esta estructura:


```plaintext
├── modulo/
    ├── aplicacion/
    │   ├── dto/
    │   └── impl/
    ├── dominio
    │   └── repo/
    ├── infraestructura/
    │   └── persistencia/
    ├── interface/
        ├── evento/
        │   ├── in/
        │   └── out/
        └── remote/
            └── rest/
```

- `aplicación`: casos de uso
- `dto`: objetos de transferencia de datos
- `impl`: implementación de servicios
- `dominio`: lógica de negocio
- `repo`: interfaces de acceso a datos
- `infraestructura`: manejo de servicios
- `persistencia`: acceso a datos
- `interface`: APIs internas y observadores
- `evento`: interfaces para gestionar eventos
- `in`: eventos que ingresan al sistema
- `out`: eventos que salen del sistema
- `remote`: comunicacion remota entre servicios
- `rest`: interfaces para servicios RESTful

---

## 🔄 Casos de Uso Principales

- `procesarPago(datosCompra)`
- `recibirNotificacionTransferenciaDesdeMedioPago(datosTransferencia)`
- `altaComercio(datosComercio)`
- `resumenVentasDiarias(comercio)`
- `montoActualVendido(comercio)`
- `realizarReclamo(textoReclamo)`
- `notificarPago()`, `notificarPagoOk()`, `notificarPagoError()`
- `consultarDepositos(comercio, rangoFechas)`

---

## 🧪 Testing

- ✅ Tests unitarios para la lógica de dominio
- ✅ Tests de integración entre módulos
- ✅ Mocks de sistemas externos para pruebas

---

## 🐳 Docker

Incluye archivos para ejecutar todo en contenedores:

```bash
docker-compose up --build

