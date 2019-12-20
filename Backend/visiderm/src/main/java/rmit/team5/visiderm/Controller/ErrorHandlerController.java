package rmit.team5.visiderm.Controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rmit.team5.visiderm.Exception.ErrorResponse;
import rmit.team5.visiderm.Exception.PatientNotFoundException;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
// this class is used to return the error to the users

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
    // return error when method argument is not valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    //  return error when method type argument mismatch
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error =
                ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ErrorResponse apiError =
                new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    // hanle json mapping
    @ExceptionHandler({JsonMappingException.class})
    public ResponseEntity<Object> handJsonMappingException (JsonMappingException e) {
        ErrorResponse errorRespone = new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), e.getMessage(), e.getLocalizedMessage());
        return new ResponseEntity<Object>(errorRespone, errorRespone.getStatus());
    }

    // handle patient not found exception
    @ExceptionHandler({PatientNotFoundException.class})
    public ResponseEntity<Object> handlePatientNotFoundException (PatientNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.toString(), e.getMessage(), e.getLocalizedMessage());
        return new ResponseEntity<Object>(errorResponse, errorResponse.getStatus());
    }



    // handle message not readable exception
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorRespone =  new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.toString(), "Message not readable", ex.getLocalizedMessage());
        return new ResponseEntity<Object>(errorRespone, errorRespone.getStatus());

    }
    // handle parase exception
    @ExceptionHandler(ParseException.class)
    public ResponseEntity<Object> handParseException (ParseException e) {
        ErrorResponse errorRespone = new ErrorResponse(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
                "Parse Error", e.getLocalizedMessage());
        return new ResponseEntity<Object>(errorRespone, errorRespone.getStatus());
    }

    // handle servlet missing parameters
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse errorRespone = new ErrorResponse(HttpStatus.BAD_REQUEST, Integer.toString(HttpStatus.BAD_REQUEST.value()),
                "Parse Error", e.getLocalizedMessage());
        return new ResponseEntity<Object>(errorRespone, errorRespone.getStatus());    }
}
