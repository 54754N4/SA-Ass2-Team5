package rmit.team5.external.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SizeValidator implements ConstraintValidator<ValidGender, Double> {
    @Override
    public void initialize(ValidGender constraintAnnotation) {

    }

    @Override
    public boolean isValid(Double size, ConstraintValidatorContext constraintValidatorContext) {
        return size > 0;   // a size should always be positive, and in our case not even 0
    }
}