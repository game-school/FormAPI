package com.denzelcode.form.window;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.element.Button;
import com.denzelcode.form.element.ImageType;
import com.denzelcode.form.event.SimpleFormButtonClickEvent;
import com.denzelcode.form.handler.IHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleWindowForm extends FormWindowSimple implements IWindowForm<SimpleWindowForm, SimpleFormButtonClickEvent> {

    List<IHandler<SimpleFormButtonClickEvent>> handlers = new ArrayList<>();

    protected String name = UUID.randomUUID().toString();

    public SimpleWindowForm(String title) {
        super(title, "");
    }

    public SimpleWindowForm(String name, String title) {
        this(name, title, "");
    }

    public SimpleWindowForm(String name, String title, String content) {
        super(title, content);

        this.name = name == null ? UUID.randomUUID().toString() : name;
    }

    public SimpleWindowForm addButton(String text) {
        Button button = new Button(this, text);

        super.addButton(button);

        return this;
    }

    public SimpleWindowForm addButton(String name, String text) {
        Button button = new Button(this, name, text);

        super.addButton(button);

        return this;
    }

    public SimpleWindowForm addButton(String name, String text, String path) {
       return addButton(name, text, ImageType.URL, path);
    }

    public SimpleWindowForm addButton(String name, String text, ImageType imageType, String path) {
        Button button = new Button(this, name, text, imageType, path);

        super.addButton(button);

        return this;
    }

    public Button getButton(String name) {
        for (ElementButton button: getButtons())
            if (((Button)button).getName().equals(name)) return ((Button)button);

        return null;
    }

    @Override
    public boolean wasClosed() {
        return super.wasClosed() || this.getResponse() == null;
    }

    @Override
    public boolean isValid(String formName) {
        return !this.wasClosed() && this.getName().equals(formName);
    }

    @Override
    public SimpleWindowForm addHandler(IHandler<SimpleFormButtonClickEvent> handler) {
        handlers.add(handler);

        return this;
    }

    @Override
    public void clearHandlers() {
        handlers.clear();
    }

    @Override
    public void dispatchHandlers(SimpleFormButtonClickEvent event) {
        handlers.forEach((handler) -> handler.handle(event));
    }

    @Override
    public List<IHandler<SimpleFormButtonClickEvent>> getWindowFormHandlers() {
//        return handlers;
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void sendTo(Player player) {
        FormAPI.sendWindow(player, this);
    }

    public Button getElement(String name) {
        return getButton(name);
    }
}
