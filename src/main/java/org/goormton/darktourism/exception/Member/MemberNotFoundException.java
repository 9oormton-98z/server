package org.goormton.darktourism.exception.member;

import org.goormton.darktourism.exception.BasicException;

import static org.goormton.darktourism.exception.ExceptionType.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends BasicException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND.getHttpStatus(), MEMBER_NOT_FOUND.getDetail());
    }
}
