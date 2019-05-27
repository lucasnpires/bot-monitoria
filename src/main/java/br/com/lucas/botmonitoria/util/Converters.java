package br.com.lucas.botmonitoria.util;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;

import br.com.lucas.botmonitoria.domain.response.PageResponse;
import br.com.twsoftware.alfred.object.Objeto;

public class Converters {
     
     @SuppressWarnings({ "rawtypes", "unchecked" })
     public static PageResponse buildPageResponse(Page p) {

          if (p == null) {

               return null;
          } else {

               PageResponse pageResponse = new PageResponse();
               pageResponse.setContent(p.getContent());
               pageResponse.setHasContent(p.hasContent());
               pageResponse.setNumber(p.getNumber());
               pageResponse.setNumberOfElements(p.getNumberOfElements());
               pageResponse.setSize(p.getSize());
               pageResponse.setTotalElements(p.getTotalElements());
               pageResponse.setTotalPages(p.getTotalPages());
               pageResponse.setFirst(p.isFirst());
               pageResponse.setLast(p.isLast());
               
               if (p.previousPageable().isPaged()) {
                    pageResponse.setPreviousPage(p.previousPageable().getPageNumber());
               }
               if (p.nextPageable().isPaged()) {
                    pageResponse.setNextPage(p.nextPageable().getPageNumber());
               }
               
               return pageResponse;
          }
     }
     
     public static String formatarDataLogEvento(LocalDateTime data) {

          String format = "";

          if (Objeto.notBlank(data)) {

               DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

               format = data.format(pattern);

          }

          return format;
     }     
}
