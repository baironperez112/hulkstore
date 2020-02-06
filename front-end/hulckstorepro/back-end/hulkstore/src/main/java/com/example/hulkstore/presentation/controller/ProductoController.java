package com.example.hulkstore.presentation.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.hulkstore.model.model.Producto;
import com.example.hulkstore.negocio.service.ProductoService;

/**
 * Esta clase es la encargada de representar el Web Service del Producto
 * Aqui se alojaran los metodos que seran llamados desde el cliente
 * @author Bayron Andres Perez  Muñoz
 */
@RestController		//Indica que esta clase es un controlador del  Web-Service del usuario
@RequestMapping("/apiProductos") 	//Nombre por el cual se accedera a ete Web-Service
public class ProductoController {

	//---------------------------------------------------------------------------------
	//Atributos
	//---------------------------------------------------------------------------------

	/**
	 * Inyeccion de dependencias para instanciar a la clase ProductoService
	 */
	@Autowired
	private ProductoService productoService;


	//---------------------------------------------------------------------------------
	//Metodos
	//---------------------------------------------------------------------------------

	/**
	 * Metodo del Web Service encargado de listar todos los productos de base de datos
	 * @param: Producto: el objeto de producto que llega como request
	 * @ResponseEntity: Para que las respuestas http code, puedan ser  modificadas
	 * @throws Exception 
	 */
	@GetMapping(value = "/todosLosProductos" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> obtenerProductos()
	{
		List<Producto> productos = new ArrayList<>();
		productos = productoService.obtenerTodosProductos();

		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
	}
	
	
	/**
	 * Metodo del Web Service encargado de guardar un producto a la base de datos
	 * @param: Producto: el objeto de producto que llega como request
	 * @ResponseEntity: Para que las respuestas http code, puedan ser  modificadas
	 * @throws Exception 
	 */
	@PostMapping(value = "/registrarProducto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) throws Exception{

		Producto produ = new Producto();
		produ = productoService.guardarProducto(producto);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produ.getId()).toUri();
		return ResponseEntity.created(location).build(); //Estas dos renglones sirven para devolver la URL de el producto q se creo
	}


	/**
	 * Metodo del web service encargado de vender un producto de la base de datos
	 * @param: idProducto: el identificador del producto
	 */
	@DeleteMapping(value = "/venderProducto/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public void venderProducto(@PathVariable int id) throws Exception{

		Producto produc = productoService.obtenerProductoById(id);
		
		if(produc == null){
			throw new Exception("ID: " + id);
		} 
		else {							
				productoService.venderProducto(produc.getId());
			
		}
	}


	/**
	 * Metodo del Web Service encargado de actualizar un producto a la base de datos
	 * @param: Producto: el objeto de producto que llega como request
	 * @ResponseEntity: Para que las respuestas http code, puedan ser  modificadas
	 * @throws Exception 
	 */
	@PutMapping(value = "/actualizarProducto", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Producto> actualizarProducto(@RequestBody Producto producto) 
	{
		productoService.actualizarProducto(producto);
		return new ResponseEntity<Producto>(HttpStatus.OK);
	}


	/**
	 * Metodo del web service encargado de obtener un producto por el codigo del mismo
	 * @param: codigo: el codigo o referencia del producto
	 * @return: el producto o registro de la base de datos
	 * @throws Exception 
	 */
	@GetMapping(value = "/obtenerProductoByCodigo/{codigo}")
	public ResponseEntity<List<Producto>> obtenerProductoByCodigo(@PathVariable int codigo) throws Exception
	{
		List<Producto> productos = new ArrayList<>();
		productos = productoService.obtenerProductoByCodigo(codigo);

		if (productos == null) {
			throw new Exception("ID: " + productos);
		}
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK); //EL OK es el sisgnificadode 200
	}
	
	
	/**
	 * Metodo del web service encargado de obtener un producto por el ID
	 * @param: codigo: el codigo o referencia del producto
	 * @return: el producto o registro de la base de datos
	 * @throws Exception 
	 */
	@GetMapping(value = "/obtenerProductoById/{id}")
	public ResponseEntity<Producto> obtenerProductoById(@PathVariable int id) throws Exception
	{
		
		Producto producto = productoService.obtenerProductoById(id);

		if (producto == null) {
			throw new Exception("ID: " + producto);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK); //EL OK es el sisgnificadode 200
	}

}
