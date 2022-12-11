package org.goormton.darktourism.exception.file;

import org.goormton.darktourism.exception.BasicException;

import static org.goormton.darktourism.exception.ExceptionType.FILE_TYPE_NOT_ALLOW;

public class FileContentTypeNotAllowedException extends BasicException {
    public FileContentTypeNotAllowedException() {
        super(FILE_TYPE_NOT_ALLOW.getHttpStatus(), FILE_TYPE_NOT_ALLOW.getDetail());
    }
}
