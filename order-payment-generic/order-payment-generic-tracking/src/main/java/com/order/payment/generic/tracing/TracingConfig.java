package com.order.payment.generic.tracing;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.handler.SpanHandler;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.Propagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    @Bean
    Sender sender(@Value("${mcp.zipkin.url}") String zipkinSenderUrl) {
        return OkHttpSender.create(zipkinSenderUrl);
    }

    @Bean
    SpanHandler spanReporter(Sender sender) {
        return AsyncZipkinSpanHandler.create(sender);
    }

    @Bean
    Tracing tracing(@Value("${mcp.zipkin.serviceName}") String serviceName, SpanHandler spanHandler) {
        BaggageField localField = BaggageField.create("local-id");
        BaggagePropagationConfig localConfig = BaggagePropagationConfig.SingleBaggageField.local(localField);

        BaggageField remoteField = BaggageField.create("client-id");
        BaggagePropagationConfig remoteConfig = BaggagePropagationConfig.SingleBaggageField.remote(remoteField);

        Propagation.Factory propagationFactory = BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY)
                .add(localConfig)
                .add(remoteConfig)
                .build();
        return Tracing.newBuilder()
                .sampler(Sampler.ALWAYS_SAMPLE)
                .localServiceName(serviceName)
                .propagationFactory(propagationFactory)
                .currentTraceContext(ThreadLocalCurrentTraceContext.create())
                .addSpanHandler(spanHandler)
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

}
