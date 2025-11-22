package com.example.actividad2_tema4.data

import com.example.actividad2_tema4.R
import com.example.actividad2_tema4.model.Tienda

object TiendasData {
    val tiendas = listOf(
        Tienda(
            id = 1,
            nombre = "El Faro de Cádiz",
            calle = "Calle San Félix, 15",
            productoPrincipal = "Pescado fresco",
            latitud = 36.5297,
            longitud = -6.2926,
            descripcion = "Restaurante emblemático de Cádiz con más de 50 años de tradición.",
            oferta = true,
            foto = R.drawable.tienda1
        ),
        Tienda(
            id = 2,
            nombre = "Mercado Central",
            calle = "Plaza de la Libertad, s/n",
            productoPrincipal = "Productos frescos",
            latitud = 36.5350,
            longitud = -6.2968,
            descripcion = "Mercado tradicional gaditano con productos locales.",
            oferta = false,
            foto = R.drawable.tienda2
        ),
        Tienda(
            id = 3,
            nombre = "Vinos de Jerez",
            calle = "Calle Ancha, 28",
            productoPrincipal = "Vinos y licores",
            latitud = 36.5325,
            longitud = -6.2945,
            descripcion = "Bodega especializada en vinos de la región de Jerez. Amplia selección de finos, manzanillas y brandies.",
            oferta = true,
            foto = R.drawable.tienda3
        ),
        Tienda(
            id = 4,
            nombre = "Librería Quijote",
            calle = "Calle Compañía, 10",
            productoPrincipal = "Libros",
            latitud = 36.5310,
            longitud = -6.2955,
            descripcion = "Librería independiente con gran variedad literaria.",
            oferta = false,
            foto = R.drawable.tienda4
        ),
        Tienda(
            id = 5,
            nombre = "La Freiduría del Puerto",
            calle = "Paseo Marítimo, 45",
            productoPrincipal = "Pescado frito",
            latitud = 36.5280,
            longitud = -6.2900,
            descripcion = "Auténtica freiduría gaditana frente al mar.",
            oferta = true,
            foto = R.drawable.tienda5
        ),
        Tienda(
            id = 6,
            nombre = "Artesanía Andaluza",
            calle = "Calle Sacramento, 22",
            productoPrincipal = "Artesanía local",
            latitud = 36.5340,
            longitud = -6.2975,
            descripcion = "Taller y tienda de artesanía andaluza tradicional.",
            oferta = false,
            foto = R.drawable.tienda6
        ),
        Tienda(
            id = 7,
            nombre = "Panadería La Española",
            calle = "Plaza de las Flores, 8",
            productoPrincipal = "Pan artesano",
            latitud = 36.5330,
            longitud = -6.2938,
            descripcion = "Panadería tradicional desde 1920 con dulces típicos.",
            oferta = true,
            foto = R.drawable.tienda7
        )
    )
}
