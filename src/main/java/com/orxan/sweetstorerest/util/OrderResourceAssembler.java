package com.orxan.sweetstorerest.util;

import com.orxan.sweetstorerest.controller.OrderController;
import com.orxan.sweetstorerest.model.Order;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order , Resource<Order>> {
    @Override
    public Resource<Order> toResource(Order order) {
        return new Resource<>(order,
                linkTo(methodOn(OrderController.class).getOrder(order.getTransactionID())).withSelfRel(),
                linkTo(methodOn(OrderController.class)).slash("orders").withRel("orders"));
    }
}
