package com.example.hulkstore.negocio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hulkstore.model.DAO.ProductoDAO;
import com.example.hulkstore.model.model.Producto;

/**
 * En esta Clase se implementan los metodos de negocio que realizaran la logica 
 * y la debida gestion con las operaciones a la base de datos y poder llevar 
 * dicha informacion a la capa de presentacion donde se exxponen los servicos
 * @author Bayron Andres Pelez Muñoz
 */
@Service
public class ProductoService {

	//-------------------------------------------------------------------------------
	//Atributos
	//-------------------------------------------------------------------------------

	/**
	 * inyeccion de dependencia para instanciar la clase DAO
	 */
	@Autowired
	private ProductoDAO productodao;


	//-------------------------------------------------------------------------------
	//Metodos
	//-------------------------------------------------------------------------------

	/**
	 * Metodo encargado de obtener la lista de todos los productos que
	 * de encuentran en la base de datos
	 * @return: Lista de tipo Usuario
	 */
	public List<Producto> obtenerTodosProductos() 
	{
		return (List<Producto>) productodao.findAll();
	}
	
	
	/**
	 * Metodo encargado de guardar un producto en la base de datos
	 * @param: producto: Objeto del producto
	 * @return: El producto que fue insertado
	 */
	public Producto guardarProducto(Producto producto) throws Exception {
		// VALIDACION USUARIO NULO
		if(producto==null) 
		{
			throw new Exception("El producto no puede estar vacio");
		}
		else {
			return productodao.save(producto);
		}	
	}


	/**
	 * Metodo encargado de vender un producto de la base de datos
	 * @param: idProducto: el identificador del producto
	 */
	public void venderProducto(int idProducto) {

		productodao.deleteById(idProducto);
	}


	/**
	 * Metodo encargado de actualizar un producto de la base de datos
	 * @param: idProduto: el identificador del producto
	 * @return: el producto actualizado
	 */
	public Producto actualizarProducto(Producto producto) {

		return productodao.save(producto);
	}

	
	/**
	 * Metodo encargado de buscar un producto de la base de datos por su ID
	 * @param: ID por el que se quiere buscar
	 * @return: El producto buscado
	 */
	public Producto obtenerProductoById(int idProducto) {
		return  productodao.obtenerProductoById(idProducto);
	}
	

	/**
	 * Metodo encargado de buscar un producto de la base de datos por su codigo
	 * @param: codigo por l que se quiere buscar
	 * @return: El producto buscado
	 */
	public List<Producto> obtenerProductoByCodigo(int codigo) {
		
		return (List<Producto>) productodao.obtenerProductoByCodigo(codigo);
	}

}

