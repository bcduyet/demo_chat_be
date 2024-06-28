package co.jp.xeex.chat.util;

import jakarta.persistence.Tuple;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for converting a list of tuples to a list of DTOs.
 * @author v_long
 *
 * @param <Dto> the type of the DTO
 */
@Log4j
public class DtoConverterUtil<Dto> {

    /**
     * Converts a list of tuples to a list of DTOs.
     *
     * @param list the list of tuples to convert
     * @param dto  the DTO object to populate with data from the tuples
     * @return the list of DTOs. Returns null if the input list is null or empty.
     */
    @SuppressWarnings("unchecked")
    public List<Dto> toList(List<Tuple> list, Dto dto) {
        if(list == null || list.isEmpty() || dto == null) {
            return null;
        }        
        Map<String, Type> fields = getFields(dto);
        List<Dto> result = new ArrayList<>();
        //
        for (Tuple tuple : list) {            
            Dto dtoInstance = null;
            try {
                dtoInstance = ((Class<Dto>) dto.getClass()).getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                log.error(e.getMessage());
            }
            if(null != dtoInstance) {
                result.add(convertDto(tuple, fields, dtoInstance));
            }           
        }
        return result;
       // return list.stream().map(tuple -> convertDto(tuple, fields, dto)).collect(Collectors.toList());
    }

    /**
     * Converts a tuple to a DTO object.
     *
     * @param tuple the tuple containing the data
     * @param dto   the DTO object to populate with data from the tuple
     * @return the DTO object
     */
    private Dto convertDto(Tuple tuple, Map<String, Type> fields, Dto dto) {        
        fields.forEach((fieldName, fieldType) -> {
            Object value = tuple.get(fieldName);
            setFieldValue(dto, fieldName, value);
        });

        return dto;
    }

    /**
     * Retrieves the fields of a DTO object.
     *
     * @param dto the DTO object
     * @return a map of field names to field types
     */
    private Map<String, Type> getFields(Dto dto) {
        return Arrays.stream(dto.getClass().getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, Field::getType));
    }

    /**
     * Sets the value of a field in a DTO object.
     *
     * @param dto       the DTO object
     * @param fieldName the name of the field
     * @param value     the value to set
     */
    private void setFieldValue(Dto dto, String fieldName, Object value) {
        try {
            String setterName = "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            Method setter = dto.getClass().getMethod(setterName, value.getClass());
            setter.invoke(dto, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
