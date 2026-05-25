package com.example;

import com.example.client.ObjectService;
import com.example.client.ProductY;
import com.example.client.SoapObjectService;

public class _MtK4elWY {

    public static void main(String[] args) throws Exception {
        String studentCode = "B22DCCN692";
        String qCode = "MtK4elWY";
        ObjectService objectService = new ObjectService();
        SoapObjectService port = objectService.getSoapObjectServicePort();

        ProductY productY = port.requestProductY(studentCode, qCode);
        Float finalPrice = productY.getPrice() * (1 + productY.getTaxRate() / 100) * (1 - productY.getDiscount() / 100);
        productY.setFinalPrice(finalPrice);
        port.submitProductY(studentCode, qCode, productY);
    }
}
