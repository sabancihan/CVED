package com.sabancihan.managementservice.mapstruct;

import lombok.RequiredArgsConstructor;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredArgsConstructor
public class Qualifiers {
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface ServerIdToServer {}

}
