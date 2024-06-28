package co.jp.xeex.chat.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Service;

import co.jp.xeex.chat.lang.resource.ResourceMessageItem;
import co.jp.xeex.chat.lang.resource.ResourceMessageLevels;
import co.jp.xeex.chat.lang.resource.ResourceMessageTypes;
import co.jp.xeex.chat.lang.resource.ResourceMessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to validate the DTO object. The validation is performed
 * using the Bean Validation API. The validation messages are retrieved from a
 * properties file.
 * @author v_long
 */
@Service
public class DtoValidateServiceImpl implements DtoValidateService {
    private final ResourceMessageService messageService;
    public DtoValidateServiceImpl(ResourceMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Validate the dto object
     * 
     * @param dto If dto is not null, perform validation, otherwise throw an
     *            exception
     * @return List<ErrorDto> List of errors if any, otherwise null (no error)
     */
    @Override
    public List<ResourceMessageItem> getErrors(ValidationAble dto, String lang) {
        // If the dto object is null, throw an exception
        if (dto == null) {
            return null;
        }
        // Get the default validator factory
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ValidationAble>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            List<ResourceMessageItem> list = new ArrayList<>();
            // Create a list of ErrorDto from the list of violations
            for (ConstraintViolation<ValidationAble> v : violations) {
                ResourceMessageItem ex = new ResourceMessageItem();
                String errorPropertyName = v.getPropertyPath().toString(); // Get the property name that caused the
                                                                           // // error
                String errorId = v.getMessage();// message from annotation only is the KEY
                String messageContent = messageService.getMessage(errorId, lang);
                // format the error message
                if (!messageContent.isEmpty()) {
                    // example: ERR_LEN_MIN_MAX=[%s=errorPropertyName]: The length must be between
                    // {min} and {max} characters.
                    messageContent = String.format(messageContent, errorPropertyName);

                    // Get the constraint values
                    Map<String, Object> constraintValues = v.getConstraintDescriptor().getAttributes();
                    for (Map.Entry<String, Object> entry : constraintValues.entrySet()) {
                        String key = entry.getKey();// keys: min, max, value,...
                        Object value = entry.getValue();// value bound by key
                        // replace the contraint value
                        if (value != null) {
                            messageContent = messageContent.replaceAll("\\{" + key + "\\}",
                                    value.toString());
                        }
                    }
                    ex.setMessageId(errorId);
                    ex.setMessage(messageContent);
                }
                ex.setSourceName(errorPropertyName);
                ex.setType(ResourceMessageTypes.VALIDATION);
                ex.setLevel(ResourceMessageLevels.ERROR);
                ex.setLang(lang);
                list.add(ex);
            }
            return list;
        }
        return null;
    }
}
