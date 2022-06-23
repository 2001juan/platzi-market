package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.Product;
import com.platzi.market.persistence.entity.Producto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import java.util.List;

@Mapper(componentModel = "spring",uses = {CategoryMapper.class})
public interface ProductMapper {
    @Mappings({
        @Mapping(source = "idProducto",target = "productId"),
        @Mapping(source = "nombre",target = "name"),
        @Mapping(source = "idCategoria",target = "categoryId"),
        @Mapping(source = "precioVenta",target = "price"),
        @Mapping(source = "cantidadStock",target = "stock"),
        @Mapping(source = "estado",target = "active"),
        @Mapping(source = "categoria",target = "category")
    })
    //recibe un Producto y lo mapea a un Product
    Product toProduct(Producto producto);
    List<Product> toProducts(List<Producto> productos);

    //el @InheritInverseConfiguration sabe que debe hacer el mapeo (@mappings) inverso al que tenemos
    @InheritInverseConfiguration
    //la siguiente línea se hace para que no haya que mapear el código de barras en este mapeo
    @Mapping(target = "codigoBarras",ignore = false)
    Producto toProducto(Product product);

}
