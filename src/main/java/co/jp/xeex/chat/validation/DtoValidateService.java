package co.jp.xeex.chat.validation;

import java.util.List;

import co.jp.xeex.chat.lang.resource.ResourceMessageItem;

public interface DtoValidateService {
    List<ResourceMessageItem> getErrors(ValidationAble dto, String lang);
}
