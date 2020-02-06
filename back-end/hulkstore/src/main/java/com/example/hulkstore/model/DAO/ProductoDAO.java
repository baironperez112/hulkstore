package com.example.hulkstore.model.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.hulkstore.model.model.Producto;

/**
 * Interfaz que hereda todos los metodos de la clase CrudRepository donde estaran
 * todos los metodos construidos y que tienen una estructura generica para recibir 
 * cualquier tipo de Objeto y realizar las operaciones DML y DLL
 * @author Bayron Andres Perez Muñoz
 */
public interface ProductoDAO extends CrudRepository<Producto, Integer>
{
	
	/**
	 * Metodo que se encarga de buscar un producto segun el codigo seleccionado
	 * @author Bayron Andres Perez Muñoz
	 */
	@Query("from Producto p where p.codigo =:codigo")
	List<Producto> obtenerProductoByCodigo(@Param("codigo")Integer codigo);

	
	/**
	 * Metodo que se encarga de buscar un producto segun el ID seleccionado
	 * @author Bayron Andres Perez Muñoz
	 */
	@Query("from Producto p where p.id =:idProducto")
	Producto obtenerProductoById(@Param("idProducto")Integer idProducto);
}
