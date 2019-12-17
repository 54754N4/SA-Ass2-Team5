package rmit.team5.external.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FaxNumberValidator implements ConstraintValidator<ValidFaxNumber, String> {
    private static final String FAX_NUMBER_PATTERN = "(([+][(]?[0-9]{1,3}[)]?)|([(]?[0-9]{4}[)]?))\\s*[)]?" +
            "[-\\s\\.]?[(]?[0-9]{1,3}[)]?([-\\s\\.]?[0-9]{3})([-\\s\\.]?[0-9]{3,4})";

    @Override
    public void initialize(ValidFaxNumber constraintAnnotation) {

    }

    @Override
    public boolean isValid(String faxNum, ConstraintValidatorContext constraintValidatorContext) {
        return validateFaxNum(faxNum);
    }

    private boolean validateFaxNum (String faxNum) {
        // only check when there is value
        if (faxNum == null || faxNum.trim().isEmpty()) return true;
        Pattern pattern = Pattern.compile(FAX_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(faxNum);
        return matcher.matches();
    }
}
