/**
 * Modelo adaptado de Diario.kt (Spring Boot)
 * Representa un Diario en la API
 */
export interface SpringBootItem {
  id?: number;                    // Long? → number (opcional para creación)
  titulo: string;                 // String = ""
  contenido: string;              // String = ""
  visibilidad: string;            // String = "PRIVADO" o "PUBLICO"
  fechaCreacion?: string | null;  // String? → opcional nullable
  fechaModificacion?: string | null;
  usuarioId?: number | null;
  proyectoId?: number | null;
  usuarioNombre?: string | null;
  temaTitulo?: string | null;
  temaId?: number | null;
}

/**
 * Modelo de paginación de Spring Boot (PageResponse<T>)
 * Mismo formato que tu API Kotlin
 */
export interface PageResponse<T> {
  content: T[];           // Array de items
  totalElements: number;  // Total de elementos
  totalPages: number;     // Total de páginas
  size: number;           // Tamaño de página
  number: number;         // Número de página actual
}