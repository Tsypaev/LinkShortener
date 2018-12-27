package ru.tsypaev.link.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Haven't request params")
public class NoRequestParams extends RuntimeException {
}
