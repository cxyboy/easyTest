package com.webui.util;

import com.webui.exception.ExecuteTimeoutException;
import com.webui.exception.FrameWorkException;
import com.webui.framework.facade.Driver;
import com.webui.framework.facade.UiElement;
import com.webui.framework.facade.Wait;
import org.openqa.selenium.support.ui.Sleeper;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;

public class ConditionWait implements Wait<UiElement> {
    public long timeOut;
    public long gap;
    public String errMessage;
    private final Clock clock;
    private final Sleeper sleeper;
    private final UiElement u;
    private int temp;


    public ConditionWait(UiElement uiElement) {
        this(uiElement, 60L, 6L);
    }

    public ConditionWait(UiElement uiElement, long timeOut, long sleep) {
        this.u = uiElement;
        this.timeOut = timeOut;
        this.gap = sleep;
        this.clock = Clock.systemDefaultZone();
        this.sleeper = Sleeper.SYSTEM_SLEEPER;
    }

    @Override
    public <R> R conditionWait(Function<? super UiElement, R> isTrue) {

        Instant endTime = clock.instant().plus(Duration.ofSeconds(timeOut));
        while (true) {
            Throwable thr = null;
            try {
                R apply = isTrue.apply(u);
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

    private void parseObject(Object o) {
        long lon = 0L;
        switch (o.getClass().getSimpleName()) {
            case "String":
                errMessage = String.valueOf(o);
                break;
            case "long":
                temp++;
                lon = temp >= 2 ? gap = Long.parseLong(String.valueOf(o)) : (timeOut = Long.parseLong(String.valueOf(o)));
                break;
        }

        if (gap > timeOut) {
            timeOut = gap;
            gap = lon;
        }


    }
}
