package ua.kiev.unicyb.diploma.converter;

import ua.kiev.unicyb.diploma.converter.Converter;

public interface ConfigurationConverter<E, D> extends Converter<E, D> {
    E toEntityWithFile(D dto, String filename);
}
