package com.lcwd.electronicstore.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;

public class ImageNameValidator implements ConstraintValidator<ImageNameValid,String> {

    private Logger logger= LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //logic

        logger.info("Message from isValid {}",value);
        if(value.isBlank()){
            return  false;
        }else {
            return  true;
        }

    }
}
