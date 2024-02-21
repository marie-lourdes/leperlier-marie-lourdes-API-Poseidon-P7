package com.nnk.springboot.domain.dto;

public interface IMapper<T, t> {
	T entityToObjectDTO(t entity);
}
