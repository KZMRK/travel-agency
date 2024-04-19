package com.kazmiruk.travel_agency.model.exception;

import com.kazmiruk.travel_agency.type.ErrorMessageType;

public class ApplicationException extends RuntimeException {

    public ApplicationException(ErrorMessageType errorMessageType, Object... messageArgs) {
        super(errorMessageType.getMessage().formatted(messageArgs));
    }

}
