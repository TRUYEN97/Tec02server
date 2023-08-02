package com.tec02.Service.impl.impl.impl;

import org.springframework.stereotype.Service;

import com.tec02.Service.impl.impl.AbsSaveDefaultLocationService;
import com.tec02.model.dto.impl.ProductDto;
import com.tec02.model.entity.impl.Product;
import com.tec02.repository.impl.ProductReponsitory;


@Service
public class ProductService extends AbsSaveDefaultLocationService<ProductDto, Product> {

	protected ProductService(ProductReponsitory reponsitory) {
		super(reponsitory, ProductDto.class, Product.class);
	}

}
