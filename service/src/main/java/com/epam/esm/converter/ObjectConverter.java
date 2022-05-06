package com.epam.esm.converter;

import com.epam.esm.exception.ServiceException;

public interface ObjectConverter<E, D> {

    D convertEntityToDto(E entity);

    E convertDtoToEntity(D dto) throws ServiceException;

}
