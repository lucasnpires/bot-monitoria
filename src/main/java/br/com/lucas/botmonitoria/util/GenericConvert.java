package br.com.lucas.botmonitoria.util;


import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.lucas.botmonitoria.domain.response.PageResponse;


public class GenericConvert {

     public static <E, T> E convertModelMapper(T source, Class<E> typeDestination) {

          E model = null;
          if (source != null && typeDestination != null) {

               ModelMapper modelMapper = new ModelMapper();

               modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
               model = modelMapper.map(source, typeDestination);
          }

          return model;
     }

     public static <E, T> E convertModelMapper(T source, Type destinationType) {

          E model = null;
          if (source != null && destinationType != null) {

               ModelMapper modelMapper = new ModelMapper();

               modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
               model = modelMapper.map(source, destinationType);
          }

          return model;
     }
     
     public static <E, T> List<E> convertWithMapping(List<T> source, Type destinationType, PropertyMap<T, E> mapping) {

          List<E> model = null;
          if (source != null && destinationType != null) {

               ModelMapper modelMapper = new ModelMapper();

               modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
               modelMapper.addMappings(mapping);
               model = modelMapper.map(source, destinationType);
          }

          return model;
     }

     public static <E, T> void convertModelMapper(T source, E destination) {

          if (source != null && destination != null) {

               ModelMapper modelMapper = new ModelMapper();

               modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
               modelMapper.map(source, destination);
          }
     }
     
     public static <E, T> List<E> convertModelMapper(List<T> source, Type destinationType) {

          List<E> model = null;
          if (source != null && destinationType != null) {

               ModelMapper modelMapper = new ModelMapper();

               modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
               model = modelMapper.map(source, destinationType);
          }

          return model;
     }

     public static <T, E> PageResponse<?> convertModelMapperToPageResponse(Page<T> page, Type typeDestination) {

          if (page != null && typeDestination != null) {

               List<E> arrayList = convertModelMapper(page.getContent(), typeDestination);
               
               Pageable pageable = PageRequest.of(page.getPageable().getPageNumber(), page.getSize());
               Page<E> pageResponse = new PageImpl<>(arrayList, pageable, page.getTotalElements());
               return Converters.buildPageResponse(pageResponse);
          }

          return null;
     }
     
     public static <T, E> PageResponse<?> convertModelMapperToPageCustom(Page<T> page, Type typeDestination, PropertyMap<T, E> mapping) {

          if (page != null && typeDestination != null) {

               List<E> arrayList = convertWithMapping(page.getContent(), typeDestination, mapping);
               Pageable pageable = PageRequest.of(page.getPageable().getPageNumber(), page.getSize());
               Page<E> pageResponse = new PageImpl<>(arrayList, pageable, page.getTotalElements());
               return Converters.buildPageResponse(pageResponse);
          }

          return null;
     }
     
     public static <T, E> E convertWithMapping(T source, Class<E> typeDestination, PropertyMap<T, E> mapping) {

          ModelMapper modelMapper = new ModelMapper();
          modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
          modelMapper.addMappings(mapping);

          return modelMapper.map(source, typeDestination);
     }


}
