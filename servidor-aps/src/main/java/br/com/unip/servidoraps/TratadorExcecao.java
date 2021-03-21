package br.com.unip.servidoraps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TratadorExcecao implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.info(t.getName(), e.getCause());
    }
}
