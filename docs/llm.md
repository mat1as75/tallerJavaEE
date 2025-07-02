
# Integración de LLM para Clasificación de Reclamos

## ¿Qué es un LLM y para qué se usa en este proyecto?

Un **LLM (Large Language Model)** es un modelo de lenguaje entrenado con grandes volúmenes de texto que puede comprender, generar y clasificar lenguaje natural. En este proyecto se utilizó un LLM local (LLaMA2) para **clasificar automáticamente mensajes de reclamo** como `POSITIVO`, `NEGATIVO` o `NEUTRO`, mejorando así la capacidad de análisis del sistema de forma automática y escalable.

## Objetivo de la Integración

El objetivo de esta funcionalidad fue automatizar la **etiquetación de quejas de clientes** usando procesamiento de lenguaje natural. De esta manera, el sistema puede tomar decisiones o priorizar reclamos según su tono, sin intervención humana.

## Instalación y Ejecución de Llama2 Localmente

A continuación se detallan los pasos seguidos para instalar y ejecutar el modelo LLaMA2 mediante **Ollama** en Linux.

### Requisitos

- Docker Engine instalado

### Pasos de instalación y ejecución

#### 1. Ejecutar contenedor de Ollama (motor)

```bash
sudo docker run -d -p 11434:11434 --name ollama ollama/ollama
```

> Ollama es un gestor de modelos de lenguaje. Expone una API REST para interactuar con modelos como LLaMA2.

#### 2. Descargar el modelo LLaMA2

```bash
sudo docker exec -it ollama ollama pull llama2
```

Esto descargará el modelo LLaMA2 necesario para realizar clasificación de texto.

#### 3. Ejecutar el modelo

```bash
sudo docker exec -it ollama ollama run llama2
```

Esto pondrá a correr el modelo y lo expondrá vía API en `http://localhost:11434`.

---

## Uso del LLM en el Proyecto

La lógica del uso del modelo se encuentra encapsulada en las siguientes clases:

### 🔹 `LLMClient.java`

Esta clase es responsable de realizar la consulta al LLM (vía HTTP) y procesar la respuesta para obtener la clasificación (`POS/NEG/NEU`).

```java
String prompt = "Dado el siguiente reclamo, respondé únicamente con una de estas 3 palabras: POSITIVO, NEGATIVO o NEUTRO. Reclamo: \"" + textoReclamo + "\"";
```

La respuesta se analiza para detectar cuál de las 3 etiquetas devuelve el modelo.

### 🔹 `ComplaintMessageConsumer.java`

Este componente es un `MessageListener` que escucha mensajes de la queue `ComplaintQueue`. Cuando llega un nuevo `ComplaintMessage`, se invoca al LLM para clasificar el texto del reclamo:

```java
qualification = LLMClient.clasificarReclamo(complaintMessage.getMessage()).toUpperCase(Locale.ROOT);
```

Luego se persiste la queja junto con su calificación mediante `CommerceRepository`.

---

## Flujo de Trabajo de la Clasificación

1. El comercio emite una queja desde la UI o API.
2. Se envía un mensaje a la **Queue JMS `ComplaintQueue`** (asincrónicamente).
3. El **Message-Driven Bean** `ComplaintMessageConsumer` consume el mensaje.
4. Se consulta al LLM mediante `LLMClient`.
5. La queja es **guardada en la base de datos** junto con su calificación (`Qualification`).

---

## Ejemplo de Request y Respuesta del LLM

### Prompt Enviado:

```json
{
  "model": "llama2",
  "prompt": "Dado el siguiente reclamo, respondé únicamente con una de estas 3 palabras: POSITIVO, NEGATIVO o NEUTRO. Reclamo: \"El sistema estuvo caído 2 horas.\"",
  "stream": false
}
```

### Respuesta Esperada:

```json
{
  "response": "NEGATIVO"
}
```

---

## Recomendaciones

- El modelo LLaMA2 debe estar ejecutándose localmente mediante Ollama para que la funcionalidad esté disponible.
- Para ambientes productivos, considerar:
    - Aumentar la tolerancia a fallos del LLM (timeout, retries).
    - Posible integración con modelos LLM en la nube si se requiere mayor disponibilidad o potencia.
- Se puede expandir la clasificación para detectar sentimientos más complejos en el futuro.

---

## Consideraciones Finales

La incorporación de un modelo LLM para analizar y clasificar reclamos agrega **valor automático** al proceso de gestión de clientes, permitiendo:
- Reacciones más inteligentes ante quejas.
- Priorización por nivel de gravedad.
- Métricas más útiles para análisis interno.

El enfoque es modular y desacoplado, por lo que puede adaptarse fácilmente a otros modelos o APIs de lenguaje.