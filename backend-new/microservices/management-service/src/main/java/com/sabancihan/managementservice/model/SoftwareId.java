package com.sabancihan.managementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Getter
@NoArgsConstructor

public class SoftwareId implements Serializable {



    private String vendor_name;
    private String product_name;

    public SoftwareId(String vendor_name, String product_name) {
        this.vendor_name = vendor_name.toLowerCase();
        this.product_name = product_name.toLowerCase();
    }


    private void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name.toLowerCase();
    }

    private void setProduct_name(String product_name) {
        this.product_name = product_name.toLowerCase();
    }


}
