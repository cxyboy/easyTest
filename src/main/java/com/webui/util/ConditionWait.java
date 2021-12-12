package com.webui.util;

import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.FrameWorkException;
import com.webui.framework.ExpectedConditions;
import com.webui.framework.facade.Wait;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class ConditionWait implements Wait {
    public long timeOut;
    public long gap;
    public String message;
    private final Clock clock;
    private final Object t;


    public ConditionWait(Object o) {
        this(o, 60L, 6L);
    }

    public ConditionWait(Object o, long timeOut, long sleep) {
        this.t = o;
        this.timeOut = timeOut;
        this.gap = sleep;
        this.clock = Clock.systemDefaultZone();
    }

    public ConditionWait setMessage(String message) {
        this.message = message;
        return this;
    }



    @Override
    @SuppressWarnings("all")
    public <T, R> R conditionWait(Function<T, R> isTrue) {
        Instant endTime = clock.instant().plus(Duration.ofSeconds(timeOut));
        while (true) {
            Throwable thr = null;
            try {
                R apply = isTrue.apply((T) t);
                if (apply != null && (!apply.getClass().equals(Boolean.class) || apply.equals(Boolean.TRUE))) {
                    return apply;
                }
            } catch (Throwable throwable) {
                thr = throwable;
            }
            if (endTime.isBefore(clock.instant())) {
                String messages = String.format("Expected condition failed: [%s] total run[%s] second with %s second interval", message, timeOut, gap);
//                String message = String.format("%s: total run[%s] second with %s second interval", errMessage != null ? errMessage : "Expected condition failed", timeOut, gap);
                throw new ExecuteTimeoutException(messages, thr);
            }
            try {
                Thread.sleep(Duration.ofSeconds(gap).toMillis());
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new FrameWorkException("currentThread declared dead!");
            }
        }
    }

}
