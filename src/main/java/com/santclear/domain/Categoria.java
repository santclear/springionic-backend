package com.santclear.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//	Serializable é um interface que indica que os objetos da classe poderam ser convertidos para uma sequência de bytes.
// Desse modo, é possível que os objetos possam ser gravados em arquivo, trafegar em rede, etc.
// 	Toda classe Serializable necessita de um número de versão, se a classe for recém criada é coerente que o valor do número de versão seja 1.
// Nesse caso: serialVersionUID = 1L
@Entity
public class Categoria implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	public Categoria() {}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	//	hashCode e Equals: Para que 2 objetos possam ser comparados pelo seu conteúdo e não o seu ponteiro de memória. 
	// Desse modo os objeto seram comparados por valor.
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
