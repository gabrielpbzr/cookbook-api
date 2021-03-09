package com.github.gabrielpbzr.cookbook.utils.converters.jpa;

import com.github.gabrielpbzr.cookbook.utils.Strings;
import java.util.UUID;
import javax.persistence.AttributeConverter;

/**
 * JPA Converter for type UUID
 * @author gabriel
 */
public class UUIDConverter implements AttributeConverter<UUID, String> {

    @Override
    public String convertToDatabaseColumn(UUID attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public UUID convertToEntityAttribute(String dbData) {
        if (Strings.isNullOrEmpty(dbData)) {
            return null;
        }
        return UUID.fromString(dbData);
    }
    
}
