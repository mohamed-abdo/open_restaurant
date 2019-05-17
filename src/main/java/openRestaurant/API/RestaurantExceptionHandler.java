package openRestaurant.API;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestaurantExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Throwable.class})
    private ResponseEntity<Object> genericHandler(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(
                "internal error, please contact administrator.",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
