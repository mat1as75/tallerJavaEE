
# Iteración 3: Observabilidad y Monitoreo

Este documento describe las tareas realizadas en la tercera iteración del Taller de Sistemas de Información, enfocadas en la **observabilidad** del sistema mediante herramientas como **Grafana**, **InfluxDB** y **Docker**.

---

### 1. Instalación de Docker

## Configurar el repositorio apt de Docker:

```
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
```
## Instalar los paquetes de Docker:

```
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```

Se instaló **Docker Engine** en el entorno local siguiendo la guía provista. Verifiqué que Docker funcionaba correctamente ejecutando:

```
docker --version
```

> 💡 *Recomiendo reiniciar el sistema tras la instalación para evitar problemas con los permisos del daemon.*

### 2. Descargar imagen Grafana + InfluxDB

Se utilizó la imagen provista en Docker Hub:    
[https://hub.docker.com/r/philhawthorne/docker-influxdb-grafana/](https://hub.docker.com/r/philhawthorne/docker-influxdb-grafana/)

```
docker pull philhawthorne/docker-influxdb-grafana
docker run -d \
  --name docker-influxdb-grafana \
  -p 3003:3003 \
  -p 3004:8083 \
  -p 8086:8086 \
  -v ~/monitoring/influxdb:/var/lib/influxdb \
  -v ~/monitoring/grafana:/var/lib/grafana \
  philhawthorne/docker-influxdb-grafana:latest
```

📸 *Agregar aquí una captura del contenedor corriendo en Docker Desktop.*

---

## 📊 Implementar dashboard

### 1. Registro de nuevos eventos

En el módulo de monitoreo, se implementaron el registro de los siguientes eventos:

- **Pagos realizados** (confirmados y rechazados)
- **Cantidad de reportes de ventas**
- **Notificaciones de depósitos en banco**
- **Reclamos realizados**

Cada evento fue registrado con su respectiva métrica y tag en InfluxDB, siguiendo el siguiente patrón:
```
meterRegistry.counter(counterName).increment()
```

### 2. Creación de dashboard

Se creó, en un archivo JSON, un dashboard personalizado con los siguientes paneles:

- 🔄 **Pagos realizados**: visualización por tipo (`confirmado`, `rechazado`).
- 📈 **Cantidad de reportes de ventas**: contador acumulativo diario.
- 🏦 **Notificaciones de depósitos**: gráfico por hora.
- 📢 **Reclamos realizados**: contador acumulativo.

📸 *Agregar aquí capturas del nuevo dashboard con los paneles correspondientes.*

---

## 🐳 Docker Compose

Se investigó la herramienta **Docker Compose** para lograr separar los contenedores de Grafana e InfluxDB. El archivo `docker-compose.yml` utilizado fue el siguiente:

```
version: '3.8'

services:
  influxdb:
    image: influxdb:2.7  # última versión estable en 2024
    container_name: influxdb
    ports:
      - "8086:8086"
    volumes:
      - influxdb_data:/var/lib/influxdb2
    environment:
      - DOCKER_INFLUXDB_INIT_MODE=setup
      - DOCKER_INFLUXDB_INIT_USERNAME=admin
      - DOCKER_INFLUXDB_INIT_PASSWORD=admin123
      - DOCKER_INFLUXDB_INIT_ORG=TallerJavaEquipo2
      - DOCKER_INFLUXDB_INIT_BUCKET=TallerJava
      - DOCKER_INFLUXDB_INIT_ADMIN_TOKEN=secret-key
    restart: unless-stopped

  grafana:
    image: grafana/grafana-oss:latest
    container_name: grafana
    ports:
      - "3000:3000"
    volumes:
      - ./grafana/dashboards:/var/lib/grafana/dashboards
      - ./grafana/provisioning:/etc/grafana/provisioning
      - grafana_data:/var/lib/grafana
    environment:
      - GF_SECURITY_ADMIN_USER=root
      - GF_SECURITY_ADMIN_PASSWORD=root
    depends_on:
      - influxdb
    restart: unless-stopped

volumes:
  influxdb_data:
  grafana_data:
```

📸 *Agregar aquí captura de ambos contenedores corriendo en Docker Compose.*

---

## 📌 Conclusiones

Esta iteración permitió comprender en profundidad el valor del monitoreo en aplicaciones reales, así como también incorporar herramientas nuevas como Docker, InfluxDB y Grafana. Además, se adquirieron habilidades clave en el armado de dashboards personalizados y configuración de métricas desde aplicaciones Java.

---

## 🖼️ Galería de imágenes

> Reemplazar las siguientes rutas por tus capturas reales:

- ![Dashboard Grafana](./docs/img/custom-dashboard.png)
- ![Docker Compose](./docs/img/docker-compose-containers.png)
- ![Docker Grafana](./docs/img/docker-grafana.png)
- ![Docker InfluxDB](./docs/img/docker-influxdb.png)

---
