package br.com.unip.servidoraps;

import java.util.concurrent.ThreadFactory;

public class FabricaThread implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r, "Servidor");
        thread.setUncaughtExceptionHandler(new TratadorExcecao());
        return thread;
    }
}
