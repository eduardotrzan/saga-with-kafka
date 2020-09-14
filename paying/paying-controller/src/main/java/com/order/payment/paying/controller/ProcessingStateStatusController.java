package com.order.payment.paying.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.payment.paying.dto.enums.ProcessingStateStatusDto;
import com.order.payment.paying.dto.response.PaymentDto;
import com.order.payment.paying.dto.response.ProcessingStateDto;
import com.order.payment.paying.service.business.ProcessingState;

@Api(value = "Processing Status Management")
@RestController
@RequestMapping({ "/v1/processingStatus" })
@RequiredArgsConstructor
public class ProcessingStateStatusController {

    private final ProcessingState processingState;

    @ApiOperation(value = "Retrieves a Processing Status by its UUID.", response = ProcessingStateDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieves a Processing Status.")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessingStateDto getStatus() {
        ProcessingState.ProcessingStateStatus processingStateStatus = processingState.getProcessingStateStatus();
        return this.build(processingStateStatus);
    }

    @ApiOperation(value = "Changes a Processing Status by its UUID.", response = PaymentDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully changes a Processing Status."),
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProcessingStateDto findOrderByUuid(@Valid @RequestBody ProcessingStateDto processingState) {
        ProcessingState.ProcessingStateStatus status = ProcessingState.ProcessingStateStatus
                .valueOf(processingState.getStatus().name());
        ProcessingState.ProcessingStateStatus newProcessingStatus = this.processingState
                .changeProcessingStateStatus(status);

        return this.build(newProcessingStatus);
    }

    private ProcessingStateDto build(ProcessingState.ProcessingStateStatus status) {
        ProcessingStateStatusDto dto = ProcessingStateStatusDto.valueOf(status.name());
        return ProcessingStateDto.builder()
                .status(dto)
                .build();
    }
}
