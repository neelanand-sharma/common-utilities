package com.nrs.utils;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author neelanand.sharma
 *
 */
public class LoggingUtil {
    private static final String ENTERING_PREFIX = "Entering {0}";
    private static final String EXITING_PREFIX = "Exiting {0}";
    private static final String ARGUMENTS = ": Arguments =>";
    private static final String ARGUMENTS_NOT_AVAILABLE = ARGUMENTS + " N/A";
    private static final String RETURN = ", Returns =>";
    private static final String RETURN_NOT_AVAILABLE = RETURN + " N/A";

    /**
     * Logs a message at WARN level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void warn(Class<?> className, String message, Object[] args) {
        Log log = LogFactory.getLog(className);
        if (log.isWarnEnabled()) {
            log.warn((null != args && args.length > 0)
                    ? MessageFormat.format(message, args)
                    : message);
        }
    }

    /**
     * Logs a message at INFO level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void info(Class<?> className, String message, Object[] args) {
        Log log = LogFactory.getLog(className);
        if (log.isInfoEnabled()) {
            log.info((null != args && args.length > 0)
                    ? MessageFormat.format(message, args)
                    : message);
        }
    }

    /**
     * Logs a message at ERROR level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void error(Class<?> className, String message,
            Object[] args) {
        error(className, message, args, null);
    }

    /**
     * Logs a message at ERROR level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     * @param e
     *            exception to be logged
     */
    public static void error(Class<?> className, String message, Object[] args,
            Throwable e) {
        Log log = LogFactory.getLog(className);
        if (log.isErrorEnabled()) {
            log.error((null != args && args.length > 0)
                    ? MessageFormat.format(message, args)
                    : message, e);
        }
    }

    /**
     * Logs a message at DEBUG level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void debug(Class<?> className, String message,
            Object[] args) {
        Log log = LogFactory.getLog(className);
        if (log.isDebugEnabled()) {
            log.debug((null != args && args.length > 0)
                    ? MessageFormat.format(message, args)
                    : message);
        }
    }

    /**
     * Logs a message at TRACE level, if enabled for {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param message
     *            message string to be logged, can be parameterized and values
     *            supplied by {@code args}
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void trace(Class<?> className, String message,
            Object[] args) {
        Log log = LogFactory.getLog(className);
        if (log.isTraceEnabled()) {
            log.trace((null != args && args.length > 0)
                    ? MessageFormat.format(message, args)
                    : message);
        }
    }

    /**
     * Logs a method entering message at TRACE level, if enabled for
     * {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param method
     *            method name of the log entry
     * @param args
     *            {@code Object} array representing the message parameters
     */
    public static void entering(Class<?> className, String method,
            Object[] args) {
        StringBuffer msgBuffer = new StringBuffer(ENTERING_PREFIX);
        Object[] temp = { method };

        if (!Util.isEmpty(args)) {
            int index = 1;
            msgBuffer.append(ARGUMENTS);
            for (Object arg : args) {
                if (index > 1) {
                    msgBuffer.append(",");
                }
                msgBuffer.append(" {").append(index).append("}");
                index++;
            }
            temp = ArrayUtils.addAll(temp, args);
        } else {
            msgBuffer.append(ARGUMENTS_NOT_AVAILABLE);
        }

        trace(className, msgBuffer.toString(), temp);
    }

    /**
     * Logs a method exiting message at TRACE level, if enabled for
     * {@code className}.
     * 
     * @param className
     *            {@code Class} name for logging
     * @param method
     *            method name of the log entry
     * @param args
     *            {@code Object} array representing the message parameters
     * @param returnValue
     *            return value from the method, if any
     */
    public static void exiting(Class<?> className, String method, Object[] args,
            Object returnValue) {
        StringBuffer msgBuffer = new StringBuffer(EXITING_PREFIX);
        Object[] temp = { method };

        int index = 1;
        if (!Util.isEmpty(args)) {
            msgBuffer.append(ARGUMENTS);
            for (Object arg : args) {
                if (index > 1) {
                    msgBuffer.append(",");
                }
                msgBuffer.append(" {").append(index).append("}");
                index++;
            }
            temp = ArrayUtils.addAll(temp, args);
        } else {
            msgBuffer.append(ARGUMENTS_NOT_AVAILABLE);
        }

        if (null != returnValue) {
            msgBuffer.append(RETURN).append(" {").append(index).append("}");
            temp = ArrayUtils.addAll(temp, new Object[] { returnValue });
        } else {
            msgBuffer.append(RETURN_NOT_AVAILABLE);
        }
        trace(className, msgBuffer.toString(), temp);
    }

}
