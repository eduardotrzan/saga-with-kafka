package com.order.payment.paying.service.business;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProcessingState {

    private static ProcessingStateStatus PROCESSING_STATE_STATUS = ProcessingStateStatus.SUCCESS;

    public ProcessingStateStatus changeProcessingStateStatus(ProcessingStateStatus processingStateStatus) {
        log.info("Setting state from={} to={}", PROCESSING_STATE_STATUS, processingStateStatus);
        PROCESSING_STATE_STATUS = processingStateStatus;
        return PROCESSING_STATE_STATUS;
    }

    public ProcessingStateStatus getProcessingStateStatus() {
        log.info("Getting state={}", PROCESSING_STATE_STATUS);
        return PROCESSING_STATE_STATUS;
    }

    public enum ProcessingStateStatus {
        FAIL,
        DELAY_SUCCESS,
        DELAY_FAIL,
        SUCCESS
    }
}
