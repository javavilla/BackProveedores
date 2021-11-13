package co.edu.unbosque.lagenericiclo4.BackProveedores.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.lagenericiclo4.BackProveedores.model.*;
import co.edu.unbosque.lagenericiclo4.BackProveedores.repository.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/proveedor")
public class ProveedorController {

    @Autowired
    ProveedorRepository proveedorRepository;

	@PostMapping("/proveedores/save")
	public ResponseEntity<Proveedor> createProveedores(@RequestBody Proveedor proveedor) {
		try {
			Proveedor _proveedor = proveedorRepository.save(proveedor);
			return new ResponseEntity<>(_proveedor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/proveedores")
	public ResponseEntity<List<Proveedor>> getProveedores(@RequestParam(required = false) String nit) {
		try {
			List<Proveedor> proveedores = new ArrayList<Proveedor>();

			if (nit == null)
				proveedorRepository.findAll().forEach(proveedores::add);
			else
				proveedorRepository.findByNitContaining(nit).forEach(proveedores::add);

			if (proveedores.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(proveedores, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/proveedores/{nit}")
	public ResponseEntity<Proveedor> updateProveedores(@PathVariable("Nit") String nit,
			@RequestBody Proveedor proveedor) {
		Optional<Proveedor> proveedorData = proveedorRepository.findByNit(nit);

		if (proveedorData.isPresent()) {
			Proveedor _proveedor = proveedorData.get();
			_proveedor.setNit(proveedor.getNit());
			_proveedor.setNombre(proveedor.getNombre());
			_proveedor.setTelefono(proveedor.getTelefono());
			_proveedor.setCiudad(proveedor.getCiudad());
			_proveedor.setDireccion(proveedor.getDireccion());
			return new ResponseEntity<>(proveedorRepository.save(_proveedor), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/proveedores/{nit}")
	public ResponseEntity<HttpStatus> deleteProveedores(@PathVariable("Nit") String nit) {
		try {
			proveedorRepository.deleteByNit(nit);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
