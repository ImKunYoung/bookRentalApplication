package com.my.rental.service.mapper;

import com.my.rental.domain.Rental;
import com.my.rental.service.dto.RentalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rental} and its DTO {@link RentalDTO}.
 */
@Mapper(componentModel = "spring")
public interface RentalMapper extends EntityMapper<RentalDTO, Rental> {}
