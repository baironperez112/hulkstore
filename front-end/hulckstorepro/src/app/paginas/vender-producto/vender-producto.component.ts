import { Component, OnInit, Inject } from '@angular/core';
import { Producto } from 'src/app/_modelo/Producto';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from 'src/app/_servicio/producto.service';

@Component({
  selector: 'app-vender-producto',
  templateUrl: './vender-producto.component.html',
  styleUrls: ['./vender-producto.component.css']
})
export class VenderProductoComponent implements OnInit {

  //-----------------------------------------------------------------------------------------
  //Variables
  //-----------------------------------------------------------------------------------------

  //variable tipo lista de los productos
  productos: Producto[] = [];

  //variable para construir el objeto que sera enviado para acualizarse al vender el producto
  producto: Producto

  //Cantidad 
  cantidad: number;

  //variable del producto seleccionado
  idProducSelect: number;

  //-----------------------------------------------------------------------------------------
  //Constructor
  //-----------------------------------------------------------------------------------------
  constructor(private route: ActivatedRoute, private router: Router ,private dialoRef: 
              MatDialogRef<VenderProductoComponent>, @Inject(MAT_DIALOG_DATA) 
              public data: Producto, private productoService: ProductoService) { }

  //-----------------------------------------------------------------------------------------
  //Metodo inicializador
  //-----------------------------------------------------------------------------------------
  ngOnInit() {

    this.listarProductos();
  }

  //-----------------------------------------------------------------------------------------
  //Metodos y funciones
  //-----------------------------------------------------------------------------------------
  
  //Metodo encargado de mostrar los productos para vender
  listarProductos(){
    this.productoService.listarProductoss().subscribe(data => {
      this.productos = data;
    });
  }

  //Metodo para realizar la venta
  vender(){

    this.productoService.obtenerProductById(this.idProducSelect).subscribe(data =>{
      this.producto = new Producto();
      this.producto.id = data.id;
      this.producto.codigo = data.codigo;
      this.producto.nombre = data.nombre;
      this.producto.descripcion = data.descripcion;
      this.producto.precio = data.precio;
      this.producto.cantidad = data.cantidad - this.cantidad;
      
      console.log(this.producto);

      this.productoService.actualizarProducto(this.producto).subscribe(data => {
        setTimeout(()=> {
          this.cerrar();
        }, 2000);
        this.productoService.listarProductoss().subscribe( products => {

          this.productoService.productoRefresh.next(products);
          this.router.navigate(['menu']);
        })
      });

    });  
  }

  cerrar(){
    this.dialoRef.close();
  }

}
