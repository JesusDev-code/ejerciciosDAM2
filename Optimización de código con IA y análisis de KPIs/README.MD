# Red Social Optimizada - Actividad 1 (Tema 5)

**Asignatura:** Desarrollo de Aplicaciones Híbridas  
**Tecnologías:** Ionic 7, Angular, TypeScript (Standalone)

## 📋 Descripción del Proyecto
Este repositorio contiene la resolución de la "Actividad 1 - Tema 5: Optimización de código con IA y análisis de KPIs". 

El objetivo principal ha sido refactorizar una aplicación heredada ("RedSocialMalasPracticas") que sufría de problemas graves de rendimiento, fugas de memoria y malas prácticas de arquitectura, transformándola en una aplicación eficiente y profesional ("RedSocialBuenasPracticas").

---

## Comparativa de Rendimiento (KPIs)

Se han analizado y optimizado tres áreas críticas según los KPIs establecidos en la asignatura:

| Indicador (KPI) |  Antes (Malas Prácticas) |  Después (Optimizado) | Mejora |
| :--- | :--- | :--- | :--- |
| **Elementos Renderizados** | **1000** (Bloqueo de UI) | **24** (Carga progresiva) | **97.6% menos carga DOM** |
| **Suscripciones Activas** | **Creciente** (>18 fugas) | **1** (Estable) | **Sin fugas de memoria** |
| **Peticiones HTTP** | **Múltiples** (Duplicadas) | **1** (Caché) | **Tráfico de red mínimo** |

---

## 🛠️ Optimizaciones Técnicas Implementadas

### 1. Gestión de Listas (DOM Virtualización)
* **Problema:** La app original usaba `*ngFor` sobre un array de 1000 elementos, colapsando la memoria.
* **Solución:** Se implementó **Infinite Scroll** (`ion-infinite-scroll`) para cargar elementos en bloques de 24.
* **Extra:** Se añadió `trackBy` en el bucle `*ngFor` para evitar repintados innecesarios del DOM.

### 2. Gestión de Memoria (RxJS)
* **Problema:** Las suscripciones a los Observables nunca se cerraban, acumulándose con cada navegación.
* **Solución:** Implementación del ciclo de vida `ngOnDestroy` y patrón de `Subscription.unsubscribe()` para liberar recursos al destruir el componente.

### 3. Arquitectura y Caché
* **Problema:** Se realizaban peticiones HTTP redundantes al servidor.
* **Solución:** Creación de una variable de caché en el servicio (`SocialBadService`). Antes de llamar a la API, se verifica si los datos ya existen en memoria, devolviéndolos instantáneamente.

---

## 📸 Evidencia Visual

### Antes: Carga Masiva (1000 items)
> *La aplicación original cargaba todos los datos de golpe, penalizando el rendimiento.*
![Malas Prácticas](../Optimización%20de%20código%20con%20IA%20y%20análisis%20de%20KPIs/recursos/malo.png)

### Después: Carga Optimizada (24 items + Caché)
> *La versión optimizada carga solo lo necesario y mantiene la memoria estable (Subs activas: 1).*
![Buenas Prácticas](../Optimización%20de%20código%20con%20IA%20y%20análisis%20de%20KPIs/recursos/bueno.png)

---

