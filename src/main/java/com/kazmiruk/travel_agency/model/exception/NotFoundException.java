package com.kazmiruk.travel_agency.model.exception;

import com.kazmiruk.travel_agency.type.ErrorMessageType;

public class NotFoundException extends ApplicationException {

    public NotFoundException(ErrorMessageType errorMessageType, Number... id) {
        super(errorMessageType, (Object[]) id);
    }

}
