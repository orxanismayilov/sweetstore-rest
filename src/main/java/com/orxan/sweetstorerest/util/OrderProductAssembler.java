package com.orxan.sweetstorerest.util;

import com.orxan.sweetstorerest.controller.OrderProductController;
import com.orxan.sweetstorerest.model.OrderProduct;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderProductAssembler implements ResourceAssembler<OrderProduct , Resource<OrderProduct>> {
    @Override
    public Resource<OrderProduct> toResource(OrderProduct orderProduct) {
        return new Resource<>(orderProduct,
                linkTo(methodOn(OrderProductController.class).getOrderProduct(orderProduct.getId())).withSelfRel(),
                linkTo(methodOn(OrderProductController.class).getOrderProducts(orderProduct.getOrderId())).withRel("ordersproducts"));
    }

}
