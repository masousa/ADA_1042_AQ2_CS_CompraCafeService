package tech.ada.bootcamp.cafe.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private String entityNotFound;

    public NotFoundException(String entityNotFound) {
        this.entityNotFound = entityNotFound;
    }

}
