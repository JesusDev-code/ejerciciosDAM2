## Descripción General

Esta aplicación incluye dos funcionalidades principales:
- Una **calculadora compacta** en el footer de la app.
- Un **gestor de clientes** con formulario validado que almacena múltiples entradas, mostrando posteriormente todos los clientes guardados.
El ejercicio pedia 1 y yo lo he modificado para que se pueda guardar varios y los datos los he añadido en dataservice para aprovechar el documento.

***

## 1. Calculadora 

### ¿Qué incluye?
- Calculadora en la parte inferior (`footer`).
- Inputs de los valores, selector de operación, botón calcular y resultados, junto con contador de incrementos.

### Ejemplo de HTML:
```html
<div class="controls-row">
  <ion-button size="small" (click)="incrementFromParent()">Incrementar</ion-button>
  <ion-input type="number" [(ngModel)]="valor1" ...></ion-input>
  <ion-select [(ngModel)]="operacion" ...>
    <!-- Opciones -->
  </ion-select>
  <ion-input type="number" [(ngModel)]="valor2" ...></ion-input>
  <ion-button ... (click)="calcular()">Calcular</ion-button>
  <span *ngIf="resultado">{{ resultado }}</span>
</div>
```

***

## 2. Gestión de Cliente (CRUD básico en memoria)

### Funcionalidad
- **Formulario Cliente** para crear nuevos clientes: nombre, apellido, email y nacionalidad (todos obligatorios y tipo string).
- **Validación automática del email** (alerta y botón deshabilitado si el email no es válido).
- Al guardar, **cada nuevo cliente se añade a una lista interna** (en el servicio).
- En la ruta de **DatosCliente**, se muestra la lista de TODOS los clientes que has guardado en esa sesión/secuencia.

### Cambios clave realizados

#### Servicio (`data.service.ts`)
- La clase `DataService` ahora tiene un array privado de clientes:
  ```typescript
  private clientes: Cliente[] = [];
  saveCliente(cli: Cliente) { this.clientes.push({...cli}); }
  getClientes(): Cliente[] { return this.clientes; }
  ```
- De este modo, **puedes guardar y mostrar varios clientes**, no solo el último.

#### Formulario Cliente (`cliente.page.ts` + `.html`)
- Al guardar, se llama a `saveCliente()` del servicio y se navega a “DatosCliente”.
- Validación de email en el TS, feedback visual y botón desactivado si no es válido.
- El formulario se puede reutilizar para varios clientes y siempre añade uno más.

#### Listado de Clientes (`datoscliente.page.ts` + `.html`)
- Al cargar, `getClientes()` recupera todos los clientes guardados y los muestra en una lista.
- Si no hay ninguno, se muestra un mensaje "No hay clientes guardados".

***

## 3. Resumen de tecnologías y patrones usados

- **Ionic + Angular standalone components** (importación explícita).
- **SCSS/CSS con flexbox** para alineación horizontal compacta.
- **Servicio (provider)** para almacenamiento en memoria de la lista de clientes (patrón singleton).
- **Validación reactiva** (variable de email en TS, *no* expresiones regulares en HTML).
- **Navegación estándar de Angular** (`this.router.navigate()`).

***

## 4. Instrucciones principales de uso

1. **Usa la calculadora en el footer:**  
   Introduce valores, escoge la operación y pulsa calcular para ver el resultado en la misma línea.
2. **Añade un nuevo cliente desde ‘Cliente’:**  
   Rellena el formulario respetando los requisitos. El botón solo se habilita si todo es válido.
3. **Consulta todos los clientes desde ‘DatosCliente’:**  
   Verás un listado con todos los que hayas guardado en esa sesión.

