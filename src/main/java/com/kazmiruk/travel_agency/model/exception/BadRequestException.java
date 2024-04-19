package com.kazmiruk.travel_agency.model.exception;

import com.kazmiruk.travel_agency.type.ErrorMessageType;

public class BadRequestException extends ApplicationException {

    public BadRequestException(ErrorMessageType errorMessageType, Object... messageArgs) {
        super(errorMessageType, messageArgs);
    }

}
