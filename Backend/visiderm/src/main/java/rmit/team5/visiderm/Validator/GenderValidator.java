package rmit.team5.visiderm.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GenderValidator implements ConstraintValidator<ValidGender, Character> {
    @Override
    public void initialize(ValidGender constraintAnnotation) {

    }

    @Override
    public boolean isValid(Character gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender == 'F' || gender == 'M';
    }
}
