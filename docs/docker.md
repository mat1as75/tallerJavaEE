
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

![Docker Compose](https://github.com/user-attachments/assets/eeb94107-7b41-4cc1-a6b8-2abe6e58e16d)

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

![Dashboards-General](https://github.com/user-attachments/assets/e8659108-41a2-4f27-afe3-cdbdeb2dd7da)

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

![Docker Grafana](https://github.com/user-attachments/assets/019dab97-54be-4d85-bec4-89f880466b6f)
![Docker InfluxDB](https://github.com/user-attachments/assets/531f6a88-f396-4db5-9666-d1b4d91108c9)

---

## 📌 Conclusiones

Esta iteración permitió comprender en profundidad el valor del monitoreo en aplicaciones reales, así como también incorporar herramientas nuevas como Docker, InfluxDB y Grafana. Además, se adquirieron habilidades clave en el armado de dashboards personalizados y configuración de métricas desde aplicaciones Java.

---

## 🖼️ Galería de imágenes

- ![Dashboards Grafana General](https://github.com/user-attachments/assets/6292077b-fef6-402c-8715-618bcc8a90b0)
- ![Dashboard Pagos Confirmados](https://github.com/user-attachments/assets/184a8f40-4ab9-4ea1-9ce6-3b3e08b511e0)
- ![Dashboard Pagos Rechazados](https://github.com/user-attachments/assets/a5486e50-320f-45da-b48e-a52cb5629e89)
- ![Dashboard Reportes Ventas](https://github.com/user-attachments/assets/38b51b98-de50-44bb-94d2-6fe6c07007a3)
- ![Dashboard Depósitos Banco](https://github.com/user-attachments/assets/2d236a38-ddfa-4f9a-a195-50e9c86d52dd)
- ![Dashboard Reclamos](https://github.com/user-attachments/assets/3fe11d08-4c5d-4f6c-a8c1-f2bd0fbcd3eb)
- ![Docker-Compose](https://github.com/user-attachments/assets/2575e946-5507-49c5-ae8f-a4295868098e)
- ![Container Grafana](https://github.com/user-attachments/assets/d1e85928-293c-41cf-ab33-2f74e56c1c12)
- ![Container InfluxDB](https://github.com/user-attachments/assets/144a3d41-0f21-4a01-adc2-b509b8a8fd17)

---
