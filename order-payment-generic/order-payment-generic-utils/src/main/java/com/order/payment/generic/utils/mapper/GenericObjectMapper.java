package com.order.payment.generic.utils.mapper;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class GenericObjectMapper extends ObjectMapper {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public GenericObjectMapper() {
        super();

        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
        setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));

        findAndRegisterModules();

        enable(SerializationFeature.INDENT_OUTPUT);
    }

}
