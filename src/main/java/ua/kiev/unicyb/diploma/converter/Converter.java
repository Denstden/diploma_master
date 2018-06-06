package ua.kiev.unicyb.diploma.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface Converter<E, D> {
    E toEntity(D dto);
    D toDto(E entity);

    default List<E> toEntities(List<D> dtos) {
        List<E> list = new ArrayList<>();

        dtos.forEach(dto -> {
            list.add(toEntity(dto));
        });

        return list;
    }
}
