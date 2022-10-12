package org.goormton.darktourism.exception.Member;

import org.goormton.darktourism.exception.BasicException;

import static org.goormton.darktourism.exception.ExceptionType.MEMBER_INFORMATION_NOT_FOUND;

public class NoAuthenticationException extends BasicException {

    public NoAuthenticationException() {
        super(MEMBER_INFORMATION_NOT_FOUND.getHttpStatus(), MEMBER_INFORMATION_NOT_FOUND.getDetail());
    }
}
