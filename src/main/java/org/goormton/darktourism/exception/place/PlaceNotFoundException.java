package org.goormton.darktourism.exception.place;

import org.goormton.darktourism.exception.BasicException;

import static org.goormton.darktourism.exception.ExceptionType.PLACE_NOT_FOUND;

public class PlaceNotFoundException extends BasicException {
    public PlaceNotFoundException() {
        super(PLACE_NOT_FOUND.getHttpStatus(), PLACE_NOT_FOUND.getDetail());
    }
}
