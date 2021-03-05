package me.gyun.ounce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AlreadyUser extends RuntimeException {

    public AlreadyUser(String message) {
        super(message);
    }

}
