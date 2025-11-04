import { Injectable } from '@angular/core';

// Modelo para CardItem
export interface CardItem {
  id: number;
  name: string;
  desc: string;
  img: string;   
}

// Modelo para Cliente
export interface Cliente {
  nombre: string;
  apellido: string;
  email: string;
  nacionalidad: string;
}

@Injectable({ providedIn: 'root' })
export class DataService {
  // CLIENTES 
  private clientes: Cliente[] = [];

  saveCliente(cli: Cliente) {
    this.clientes.push({ ...cli }); 
  }

  getClientes(): Cliente[] {
    return this.clientes;
  }

  // CARD ITEMS 
  getByCategory(cat: string): CardItem[] {
    switch ((cat || '').toLowerCase()) {
      case 'servicios':
        return [
          { id: 1, name: 'Administración pública', desc: 'Gestiones con la Administración', img: 'https://images.unsplash.com/photo-1529078155058-5d716f45d604?q=80&w=600&auto=format&fit=crop' },
          { id: 2, name: 'Alimentación y ocio', desc: 'Restauración y ocio nocturno', img: 'https://images.unsplash.com/photo-1498654896293-37aacf113fd9?q=80&w=600&auto=format&fit=crop' },
          { id: 3, name: 'Alojamiento y turismo', desc: 'Hoteles y transporte turístico', img: 'https://images.unsplash.com/photo-1586773860418-d37222d8fce3?q=80&w=600&auto=format&fit=crop' },
          { id: 4, name: 'Comercio y retail', desc: 'Tiendas y comercios locales', img: 'https://images.unsplash.com/photo-1522199755839-a2bacb67c546?q=80&w=600&auto=format&fit=crop' },
          { id: 5, name: 'Salud y bienestar', desc: 'Clínicas, ópticas y gimnasios', img: 'https://images.unsplash.com/photo-1586773860418-d37222d8fce3?q=80&w=600&auto=format&fit=crop' }
        ];
      case 'restaurantes':
        return [
          { id:101, name:'La Bahía',   desc:'Pescado y marisco · Paseo marítimo', img:'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?q=80&w=600&auto=format&fit=crop' },
          { id:102, name:'Casa Román', desc:'Cocina gaditana · Centro',           img:'https://images.unsplash.com/photo-1528605248644-14dd04022da1?q=80&w=600&auto=format&fit=crop' },
          { id:103, name:'El Frito',   desc:'Freiduría clásica · La Viña',        img:'https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=600&auto=format&fit=crop' },
          { id:104, name:'Taberna Faro',desc:'Tapas y vinos · Alameda',           img:'https://images.unsplash.com/photo-1559339352-11d035aa65de?q=80&w=600&auto=format&fit=crop' },
          { id:105, name:'Mar de Cádiz',desc:'Arroces y atún rojo',               img:'https://images.unsplash.com/photo-1504674900247-0877df9cc836?q=80&w=600&auto=format&fit=crop' }
        ];
      case 'hoteles':
        return [
          { id:201, name:'Hotel Atlántico', desc:'4★ frente a la playa', img:'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=600&auto=format&fit=crop' },
          { id:202, name:'Hotel Centro',    desc:'3★ casco histórico',   img:'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=600&auto=format&fit=crop' },
          { id:203, name:'Hostal La Viña',  desc:'Económico y céntrico', img:'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?q=80&w=600&auto=format&fit=crop' },
          { id:204, name:'Boutique Caleta', desc:'Vistas al mar',        img:'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=600&auto=format&fit=crop' },
          { id:205, name:'Apart. Cádiz Sol',desc:'Familias y estancias', img:'https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=600&auto=format&fit=crop' }
        ];
      case 'clinicas':
        return [
          { id:301, name:'Clínica Atlántico', desc:'Fisioterapia y podología', img:'https://images.unsplash.com/photo-1584982751601-97dcc096659c?q=80&w=600&auto=format&fit=crop'  },
          { id:302, name:'Cádiz Dental',      desc:'Odontología integral',     img:'https://images.unsplash.com/photo-1588776814546-1ffcf47267a5?q=80&w=600&auto=format&fit=crop' },
          { id:303, name:'Visión Sur',        desc:'Óptica y audiología',      img:'https://images.unsplash.com/photo-1579154204601-01588f351e67?q=80&w=600&auto=format&fit=crop' },
          { id:304, name:'Salud La Caleta',   desc:'Medicina general',         img:'https://images.unsplash.com/photo-1584982751601-97dcc096659c?q=80&w=600&auto=format&fit=crop' },
          { id:305, name:'Sport Clinic',      desc:'Traumatología deportiva',  img:'https://images.unsplash.com/photo-1579154204601-01588f351e67?q=80&w=600&auto=format&fit=crop' }
        ];
      default:
        return [];
    }
  }
}
