package com.orxan.sweetstorerest.model;

import java.math.BigDecimal;

public interface OrderProductSummary {
    String getDescription();
    Double getTotalDiscount();
    BigDecimal getTotalPrice();
}
