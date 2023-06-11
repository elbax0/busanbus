package dev.elbax0.busanbus.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Component;

@Component
public class XmlParseUtil {

    private final XmlMapper xmlMapper = new XmlMapper();

    public <T> T readValue(String string, Class<T> valueType) {
        try {
            return xmlMapper.readValue(string, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
