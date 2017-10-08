package com.santclear.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.santclear.domain.Cliente;
import com.santclear.dto.ClienteDTO;
import com.santclear.repositories.ClienteRepository;
import com.santclear.services.exceptions.DataIntegrityException;
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
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		// O método save do Spring Data realiza operações de save e update. Se o id for nulo ele salva e se não for atualiza.
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	// Page encapsula paginação. A contagem de página inicia em zero. O atributo direction é o tipo de ordenação descentende ou ascendente
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
