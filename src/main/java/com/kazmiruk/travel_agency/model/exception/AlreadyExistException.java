package com.kazmiruk.travel_agency.model.exception;

import com.kazmiruk.travel_agency.type.ErrorMessageType;

public class AlreadyExistException extends ApplicationException {

    public AlreadyExistException(ErrorMessageType errorMessageType, String arg) {
        super(errorMessageType, arg);
    }

}
