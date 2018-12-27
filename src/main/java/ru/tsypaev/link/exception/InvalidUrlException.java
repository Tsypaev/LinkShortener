package ru.tsypaev.link.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Invalid URL")
public class InvalidUrlException extends RuntimeException {
}
