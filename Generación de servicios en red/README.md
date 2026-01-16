# Actividad 1 - Unidad 4: Generación de Servicios en Red

Este repositorio contiene los ejercicios prácticos desarrollados en Python para la Unidad 4, enfocados en la programación de servicios y procesos en red.

## Contenido del Repositorio

El proyecto consta de dos herramientas con interfaz gráfica (GUI) desarrollada en **Tkinter**.

### 1. Cliente SMTP Gráfico (`cliente_smtp.py`)
Implementación de un cliente de correo electrónico básico que conecta con un servidor SMTP (Mailtrap) para pruebas de envío seguro.

### 2. NetworkLab (`network_lab.py`)
Herramienta de laboratorio de redes que integra:
* **DNS:** Resolución de nombres de dominio a IP y viceversa.
* **NTP:** Consulta de hora oficial a servidores atómicos.

---

## Evidencias de Funcionamiento

A continuación se muestran las capturas de pantalla que demuestran el correcto funcionamiento de los scripts.

### Ejercicio 2: Envío de Correo (SMTP)
**Interfaz del programa enviando el correo:**
![Cliente SMTP](../Generación%20de%20servicios%20en%20red/recursos/envio.png)

**Buzón de Mailtrap con el mensaje recibido:**
![Inbox Mailtrap](../Generación%20de%20servicios%20en%20red/recursos/buzon.png)

### Ejercicio 3: NetworkLab (DNS y NTP)
**Prueba de resolución DNS:**
![Prueba DNS](../Generación%20de%20servicios%20en%20red/recursos/consulta_dns.png)

**Prueba de sincronización NTP:**
![Prueba NTP](../Generación%20de%20servicios%20en%20red/recursos/consulta_ntp.png)

---

## Requisitos e Instalación

Para ejecutar estos scripts necesitamos tener instalado **Python 3.x**.

### Dependencias
El ejercicio `network_lab.py` requiere la librería externa `ntplib`. Pudiendola instalar ejecutando:

```bash
pip install ntplib