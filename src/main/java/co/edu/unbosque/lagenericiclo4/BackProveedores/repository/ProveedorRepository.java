package co.edu.unbosque.lagenericiclo4.BackProveedores.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import co.edu.unbosque.lagenericiclo4.BackProveedores.model.*;

public interface ProveedorRepository extends MongoRepository<Proveedor, String> {
	List<Proveedor> findByNitContaining(String nit); //buscar elemento por cedula
	Optional<Proveedor> findByNit(String nit); //actualizar por cedula
	void deleteByNit(String nit); //eliminar documento por cedula
}
