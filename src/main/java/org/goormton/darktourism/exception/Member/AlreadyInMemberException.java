package org.goormton.darktourism.exception.Member;

import org.goormton.darktourism.exception.BasicException;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.goormton.darktourism.exception.ExceptionType.DUPLICATED_NICKNAME;
import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(CONFLICT)
public class AlreadyInMemberException extends BasicException {
    public AlreadyInMemberException() {
        super(DUPLICATED_NICKNAME.getHttpStatus(), DUPLICATED_NICKNAME.getDetail());
    }
}
