package br.com.lucas.botmonitoria.domain.response;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

     @JsonInclude(Include.ALWAYS)
     public int number;

     @JsonInclude(Include.ALWAYS)
     public int size;

     @JsonInclude(Include.ALWAYS)
     public int totalPages;

     @JsonInclude(Include.ALWAYS)
     public int numberOfElements;

     @JsonInclude(Include.ALWAYS)
     public long totalElements;

     @JsonInclude(Include.ALWAYS)
     public boolean hasContent;

     @JsonInclude(Include.ALWAYS)
     public boolean first;

     @JsonInclude(Include.ALWAYS)
     public boolean last;

     @JsonInclude(Include.ALWAYS)
     public int nextPage;

     @JsonInclude(Include.ALWAYS)
     public int previousPage;

     public List<T> content;
}
