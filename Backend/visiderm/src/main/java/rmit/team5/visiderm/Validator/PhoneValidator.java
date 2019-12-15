package rmit.team5.visiderm.Validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {
    private static final String PHONE_PATTERN = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$";

    @Override
    public void initialize(ValidPhone constraintAnnotation) {
    }
    @Override
    public boolean isValid(String phoneNum, ConstraintValidatorContext context){
        return (validatePhone(phoneNum));
    }

    private boolean validatePhone(String phoneNum) {
        // only check when the they have phone number
        if (phoneNum == null || phoneNum.isEmpty()) return true;
        else {
            Pattern pattern = Pattern.compile(PHONE_PATTERN);
            Matcher matcher = pattern.matcher(phoneNum);
            return matcher.matches();
        }
    }

}
