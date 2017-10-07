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
	
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado Id: "+ id +", Tipo: "+ Categoria.class.getName());
		}
		return obj;
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		// O método save do Spring Data realiza operações de save e update. Se o id for nulo ele salva e se não for atualiza.
		return repo.save(obj);
	}
}
