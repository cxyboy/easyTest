package com.webui.framework.facade;


import java.util.function.Function;

public interface Wait<T> {


    <R> R conditionWait(Function<? super T, R> function);


}
