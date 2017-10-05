package com.santclear.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santclear.domain.Categoria;
import com.santclear.repositories.CategoriaRepository;
import com.santclear.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado Id: "+ id +", Tipo: "+ Categoria.class.getName());
		}
		return obj;
	}
}
