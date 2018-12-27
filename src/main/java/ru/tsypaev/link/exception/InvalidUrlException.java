package ru.tsypaev.link.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Not correct URL")
public class InvalidUrlException extends RuntimeException {
}
