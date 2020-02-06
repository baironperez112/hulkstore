import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from 'src/app/_servicio/producto.service';
import { Producto } from 'src/app/_modelo/Producto';

@Component({
  selector: 'app-registrar-producto',
  templateUrl: './registrar-producto.component.html',
  styleUrls: ['./registrar-producto.component.css']
})
export class RegistrarProductoComponent implements OnInit {

  //----------------------------------------------------------------------------------------
  //Variables
  //----------------------------------------------------------------------------------------
  id: number;
  codigo: number;
  nombre: string;
  descripcion: string;
  precio: number;
  cantidad: number;


  //----------------------------------------------------------------------------------------
  //Contructor
  //----------------------------------------------------------------------------------------
  constructor(private productoService: ProductoService, private route: ActivatedRoute,
               private router: Router) { }

  //----------------------------------------------------------------------------------------
  //Metodo inicializador
  //----------------------------------------------------------------------------------------
  ngOnInit() {

  }

  registrarProducto(){
    let product = new Producto;
    product.id = this.id;
    product.codigo = this.codigo;
    product.nombre = this.nombre;
    product.descripcion = this.descripcion;
    product.precio = this.precio;
    product.cantidad = this.cantidad;
    console.log(product);

    this.productoService.registrarProducto(product).subscribe(data => {  
      setTimeout(()=> {
        this.limpiarControles();
      }, 2000);
      //Para volver a listar todos los productos con el ya creado
      this.productoService.listarProductoss().subscribe(products => {
        //guardamos en la variable refresh los datos nuevo
        this.productoService.productoRefresh.next(products);
        this.router.navigate(['menu']);
        this.productoService.productoMensaje.next('SE REALIZO LA INSERCION');
      })
    });
  }

  limpiarControles(){
    this.codigo =0;
    this.nombre = "";
    this.descripcion = "";
    this.precio = 0;
    this.cantidad = 0;

  }

}
