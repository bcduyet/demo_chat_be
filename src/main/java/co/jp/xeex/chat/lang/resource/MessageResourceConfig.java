package co.jp.xeex.chat.lang.resource;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to load message resources from the classpath.<br>
 * The message resources are used to store messages that are displayed to the
 * user.<br>
 * The messages are stored in properties files.<br>
 * The properties files are named as follows:<br>
 * message_validate_{lang}.properties<br>
 * message_bussiness_{lang}.properties<br>
 * message_information_{lang}.properties<br>
 * message_system_exception_{lang}.properties<br>
 * where {lang} is the language code (e.g. en, ja, vi, ...)<br>
 * The properties files are stored in the resources folder of the project.<br>
 * 
 * @author v_long
 */
@Log4j
@Configuration
public class MessageResourceConfig {
    // The pattern to search for message resources
    private static final String VALIDATE_MSG_FILE_NAME_PATTERN = "classpath*:message_validate_*.properties";
    private static final String BUSSINESS_MSG_FILE_NAME_PATTERN = "classpath*:message_bussiness_*.properties";
    private static final String INFORMATION_MSG_FILE_NAME_PATTERN = "classpath*:message_information_*.properties";
    private static final String SYS_EXCEPTION_MSG_FILE_NAME_PATTERN = "classpath*:message_system_exception_*.properties";

    private ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    private int count = 0;

    // utility method to build a list of MessageItem from a list of resources
    private List<ResourceMessageItem> buildMessageResources(Resource[] resources, ResourceMessageLevels level, ResourceMessageTypes type) {
        if (resources == null || resources.length == 0) {
            return new ArrayList<>();
        }
        log.debug("Start loading message resources files...");
        List<ResourceMessageItem> messageItems = new ArrayList<>();
        for (Resource resource : resources) {
            String fileName = resource.getFilename();
            String lang = null!=fileName? fileName.substring(fileName.lastIndexOf('_') + 1, fileName.lastIndexOf('.')):null;
            String baseName = null!=fileName && fileName.contains(".") ? fileName.substring(0, fileName.lastIndexOf('.')) : null;
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, Locale.forLanguageTag(lang));
            Enumeration<String> keys = bundle.getKeys();

            
            while (keys.hasMoreElements()) {
                String key = keys.nextElement();
                String message = bundle.getString(key);
                ResourceMessageItem item = new ResourceMessageItem();
                item.setLang(lang);
                item.setMessageId(key);
                item.setMessage(message);
                item.setLevel(level);
                item.setType(type);
                messageItems.add(item);
            }
        }
        log.debug("Done: Loading message resources files");
        return messageItems;
    }

    @Bean
    public List<ResourceMessageItem> invalidInputMessageResources() {
        Resource[] exceptionResources = null;
        try {
            exceptionResources = resolver.getResources(VALIDATE_MSG_FILE_NAME_PATTERN);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return buildMessageResources(exceptionResources, ResourceMessageLevels.ERROR, ResourceMessageTypes.VALIDATION);
    }

    @Bean
    public List<ResourceMessageItem> bussinessMessageResources() {
        Resource[] exceptionResources = null;
        try {
            exceptionResources = resolver.getResources(BUSSINESS_MSG_FILE_NAME_PATTERN);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return buildMessageResources(exceptionResources, ResourceMessageLevels.ERROR, ResourceMessageTypes.BUSINESS);
    }

    @Bean
    public List<ResourceMessageItem> informationMessageResources() {
        Resource[] exceptionResources = null;
        try {
            exceptionResources = resolver.getResources(INFORMATION_MSG_FILE_NAME_PATTERN);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return buildMessageResources(exceptionResources, ResourceMessageLevels.INFO, ResourceMessageTypes.GENERAL);
    }

    @Bean
    public List<ResourceMessageItem> systemExceptionMessageResources() {
        Resource[] exceptionResources = null;
        try {
            exceptionResources = resolver.getResources(SYS_EXCEPTION_MSG_FILE_NAME_PATTERN);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return buildMessageResources(exceptionResources, ResourceMessageLevels.ERROR, ResourceMessageTypes.SYSTEM);
    }
    
    @Bean
    public List<ResourceMessageItem> allMessageResourcesList() {
        List<ResourceMessageItem> allMessageResources = new ArrayList<>();
        allMessageResources.addAll(invalidInputMessageResources());
        allMessageResources.addAll(bussinessMessageResources());
        allMessageResources.addAll(informationMessageResources());
        allMessageResources.addAll(systemExceptionMessageResources());
        //more here...
        return allMessageResources;
    }

    @Bean
    public Map<String, List<ResourceMessageItem>> allMessageResourcesMap() {
        Map<String, List<ResourceMessageItem>> map = new HashMap<>();
       
        this.count = allMessageResourcesList().size();
        // group by lang
        map = allMessageResourcesList().stream().collect(Collectors.groupingBy(item -> item.getLang()));
        return map;
    }

    @Bean 
    public int count() {
        return this.count;
    }

    
}
