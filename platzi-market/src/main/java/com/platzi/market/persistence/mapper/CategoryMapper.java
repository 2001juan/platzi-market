package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Category;
import com.platzi.market.persistence.entity.Categoria;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    //Incluyo los mapeos a realizar
    @Mappings({
     @Mapping(source="idCategoria",target="categoryId"),
     @Mapping(source="descripcion",target="category"),
     @Mapping(source="estado",target="active"),
     })
        //recibe una categoria y la mapea a un category
    Category toCategory(Categoria categoria);
    @InheritInverseConfiguration
    @Mapping(target = "productos",ignore = true)
    Categoria categoria(Category toCategory);

}
