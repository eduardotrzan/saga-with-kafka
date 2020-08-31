package com.order.payment.generic.log.mdc;

/**
 * Generic handlers for Log MDC constants.
 */
public final class MdcConstants {

    private MdcConstants() {
    }

    /**
     * User token session that is logged for tracking flow in different requests.
     */
    public static final String MDC_USER_SESSION_ID = "userSessionId";

    /**
     * User token session that is logged per request calls.
     */
    public static final String REQ_SESSION_ID_KEY = "reqSessionId";
}
