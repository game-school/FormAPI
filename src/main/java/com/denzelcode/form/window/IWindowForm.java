package com.denzelcode.form.window;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindow;
import com.denzelcode.form.event.FormEvent;
import com.denzelcode.form.handler.IHandler;

import java.util.List;

@SuppressWarnings("rawtypes")
public interface IWindowForm<F extends IWindowForm, T extends FormEvent<?>> {

    String getName();

    void sendTo(Player player);

    boolean wasClosed();

    boolean isValid(String formName);

    F addHandler(IHandler<T> handler);

    void clearHandlers();

    void dispatchHandlers(T event);

    List<IHandler<T>> getWindowFormHandlers();
}
