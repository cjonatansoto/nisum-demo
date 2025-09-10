package com.nisum.technical.exercise.application.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TraceIdFilterTest {

    private TraceIdFilter traceIdFilter;
    private HttpServletRequest request;
    private ServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        traceIdFilter = new TraceIdFilter();
        request = mock(HttpServletRequest.class);
        response = mock(ServletResponse.class);
        filterChain = mock(FilterChain.class);
        MDC.clear();
    }

    @Test
    void testDoFilterExistingTraceId() throws IOException, ServletException {
        String existingTraceId = "existing-trace-id-123";
        when(request.getHeader("X-Trace-Id")).thenReturn(existingTraceId);
        traceIdFilter.doFilter(request, response, filterChain);
        assertThat(MDC.get(TraceIdFilter.TRACE_ID)).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterGenerateNewTraceId() throws IOException, ServletException {
        when(request.getHeader("X-Trace-Id")).thenReturn(null);
        traceIdFilter.doFilter(request, response, filterChain);
        assertThat(MDC.get(TraceIdFilter.TRACE_ID)).isNull();
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testDoFilterTraceIdIsSetDuringFilterExecution() throws IOException, ServletException {
        when(request.getHeader("X-Trace-Id")).thenReturn(null);
        doAnswer(invocation -> {
            String traceId = MDC.get(TraceIdFilter.TRACE_ID);
            assertThat(traceId).isNotNull();
            assertThat(traceId).matches("[0-9a-fA-F\\-]{36}"); // UUID v4 pattern
            return null;
        }).when(filterChain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        traceIdFilter.doFilter(request, response, filterChain);
        assertThat(MDC.get(TraceIdFilter.TRACE_ID)).isNull();
    }
}
