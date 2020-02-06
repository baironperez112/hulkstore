import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource, MatPaginator, MatSort, MatSnackBar, MatDialog } from '@angular/material';
import { Producto } from 'src/app/_modelo/Producto';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductoService } from 'src/app/_servicio/producto.service';
import { ActualizarProductoComponent } from './actualizar-producto/actualizar-producto.component';
import { VenderProductoComponent } from '../vender-producto/vender-producto.component';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  //-------------------------------------------------------------------------------------
  //Variables
  //-------------------------------------------------------------------------------------

  //vairable de tipo producto ara llenar la data
  produc: Producto[] = [];

  //objeto para llenar la info del producto para enviar a la ventana emergente
  producto: Producto = new Producto();

  //Variable que amarra a la tabla comvirtiendola con los datos traidos por el servicio
  dataSource: MatTableDataSource<Producto>;  

  //los nombres de las columnas que voy a mostrar (AQUI BUSCA EL METODO (*matColumnDef)
  displayedColumns = ['id', 'codigo', 'nombre', 'descripcion', 'precio', 'cantidad', 'accion'];
  
  //Paginador
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  //-------------------------------------------------------------------------------------
  //Contructor
  //-------------------------------------------------------------------------------------

  constructor( private productoService: ProductoService, private snackBar: MatSnackBar, 
               private route: ActivatedRoute, private router: Router,private dialog: MatDialog){}

  //-------------------------------------------------------------------------------------
  //Metodo inicializador
  //-------------------------------------------------------------------------------------

  ngOnInit() {
    
    //eSe hace para refrescar depues de que sen hace un update o un create a un registro 
    this.productoService.productoRefresh.subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });

    //para ennviar mensaje de actualizacion
    this.productoService.productoMensaje.subscribe(data => { 
      this.snackBar.open(data, 'Aviso', {duration: 2000});
    });

    //METOO QUE LISTA LOS USUARIOS 
    //this.usuarioService.listar().subscribe(data => console.log(data));    
    this.productoService.listarProductoss().subscribe(data => {
      this.dataSource = new MatTableDataSource(data);
      
      this.produc = data;

      //Se realiza este siclo para llenar un objeto con la info del producto para la ventana emergente
      for(let i =0 ; i< this.produc.length; i++){
        let index = this.produc[i];
        this.producto.id = index.id;
        this.producto.codigo = index.codigo;
        this.producto.nombre = index.nombre;
        this.producto.descripcion = index.descripcion;
        this.producto.precio = index.precio;
        this.producto.cantidad = index.cantidad;  
      }
      //para a paginacion
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    }); 
  }

  //-------------------------------------------------------------------------------------
  //Metododos y Funciones
  //-------------------------------------------------------------------------------------

  //Metodo para el filtro
  applyFilter(filterValue: string){
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  //Metodo para eliminar un producto
  eliminar(id: number){
    this.productoService.venderProducto(id).subscribe(data => {
      this.productoService.listarProductoss().subscribe(data => {
        this.productoService.productoRefresh.next(data);
      });
      this.productoService.productoMensaje.next('Se elimino el producto');
      this.router.navigate(['menu']);
    });
  }


  venderProducto(){
    console.log("produllllcto");
    this.dialog.open(VenderProductoComponent), {
      height:'670px',
      width: '1000px',
      disableClose: true
    }
  }

}
