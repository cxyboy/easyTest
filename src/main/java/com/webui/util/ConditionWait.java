package com.webui.util;

import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.FrameWorkException;
import com.webui.framework.facade.Wait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public abstract class ConditionWait implements Wait<Object> {
    public long timeOut = 60L;
    public long gap = 6L;
    public String errMessage;
    private final Clock clock = Clock.systemDefaultZone();
    private final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;


    @Override
    public <R> R conditionWait(Function<? super Object, R> isTrue) {

        Instant endTime = clock.instant().plus(Duration.ofSeconds(timeOut));
        while (true) {
            Throwable thr = null;
            try {
                R apply = isTrue.apply(this);
                if (apply != null && (!apply.getClass().equals(Boolean.class) || apply.equals(Boolean.TRUE))) {
                    return apply;
                }
            } catch (Throwable throwable) {
                thr = throwable;
            }
            if (endTime.isBefore(clock.instant())) {
                String message = String.format("%s: total run[%s] second with %s second interval", errMessage != null ? errMessage : "Expected condition failed", timeOut, gap);
                throw new ExecuteTimeoutException(message, thr);
            }
            try {
                sleeper.sleep(Duration.ofSeconds(gap));
            } catch (InterruptedException exception) {
                Thread.currentThread().interrupt();
                throw new FrameWorkException("currentThread declared dead!");
            }
        }
    }


}
