package com.sabancihan.managementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@AllArgsConstructor
@Data
@NoArgsConstructor

public class SoftwareId implements Serializable {
    private String vendor_name;
    private String product_name;



}
