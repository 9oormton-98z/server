package org.goormton.darktourism.exception.badge;

import org.goormton.darktourism.exception.BasicException;

import static org.goormton.darktourism.exception.ExceptionType.PLACE_NOT_FOUND;

public class PlaceAlreadyVisitedException extends BasicException {
    public PlaceAlreadyVisitedException() {
        super(PLACE_NOT_FOUND.getHttpStatus(), PLACE_NOT_FOUND.getDetail());
    }
}
