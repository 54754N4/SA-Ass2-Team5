package rmit.team5.external.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TitleValidator implements ConstraintValidator<ValidTitle, String> {
    private static final String[] TITLE_ARR = {"Mr", "Mrs", "Ms"};

    @Override
    public void initialize(ValidTitle constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return validateTitle(s);
    }

    private boolean validateTitle (String title) {
        if (title == null || title.trim().isEmpty()) return false;
        else return title.equals(TITLE_ARR[0]) || title.equals(TITLE_ARR[1]) || title.equals(TITLE_ARR[2]);
    }
}
