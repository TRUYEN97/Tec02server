package com.tec02.api.v1.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tec02.Service.impl.BaseService;
import com.tec02.api.v1.BaseApiV1;
import com.tec02.model.dto.impl.ProductDto;
import com.tec02.model.entity.impl.Product;

@RestController
@RequestMapping("api/v1/product")
public class ProductAPI extends BaseApiV1<ProductDto, Product> {
	protected ProductAPI(BaseService<ProductDto, Product> service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

}
