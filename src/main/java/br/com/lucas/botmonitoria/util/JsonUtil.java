package br.com.lucas.botmonitoria.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonUtil {

     @Autowired
     private ObjectMapper objectMapper;

     public <T> List<T> parseAsListOf(String json, Class<T> klass) {

          try {
               List<T> result = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, klass));
               return result;
          } catch (Exception e) {
               return null;
          }
     }

     public <T> T parseAs(String json, Class<T> klass) {

          try {
               return objectMapper.readValue(json, klass);
          } catch (Exception e) {
               return null;
          }
     }

     public String toJsonString(Object o) {

          try {
               return objectMapper.writeValueAsString(o);
          } catch (Exception e) {
               return null;
          }
     }
}
