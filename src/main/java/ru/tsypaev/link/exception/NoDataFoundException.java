package ru.tsypaev.link.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Link not found")
public class NoDataFoundException extends RuntimeException {

}
