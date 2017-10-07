package com.santclear.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santclear.domain.Cliente;
import com.santclear.repositories.ClienteRepository;
import com.santclear.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado Id: "+ id +", Tipo: "+ Cliente.class.getName());
		}
		return obj;
	}
}
