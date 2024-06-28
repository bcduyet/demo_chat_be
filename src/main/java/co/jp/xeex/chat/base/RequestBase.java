package co.jp.xeex.chat.base;

import co.jp.xeex.chat.validation.DtoValidateConsts;
import co.jp.xeex.chat.validation.ValidationAble;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * Represents an basics HTTP request.
 * @author v_long
 */
public class RequestBase implements ValidationAble {
     //Get from token
     /**
      * UserName that make the request      
      */
     @Length(min = 1, max = 50)
     @Pattern(regexp = DtoValidateConsts.PATTERN_HW_NO_SPACE, message = DtoValidateConsts.VALIDATE_ERR_HW_NO_SPACE)
     public String requestBy;
     /**
      * The language code.      
      */
     @NotBlank(message = DtoValidateConsts.VALIDATE_ERR_EMPTY)
     @Length(min = 1, max = 2)
     public String lang;
     
     //set by client
     /**
      * The function id.  
      */
      public String functionId;
     //more....
}
