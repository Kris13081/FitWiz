package uni.graduate.fitwiz.validation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import uni.graduate.fitwiz.model.dto.UserEntityDto;
import uni.graduate.fitwiz.validation.PasswordMatches;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }
    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        UserEntityDto user = (UserEntityDto) obj;
        return user.getPassword().equals(user.getConfirmPassword());
    }
}
