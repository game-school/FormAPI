package com.denzelcode.form;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.Element;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;
import com.denzelcode.form.listener.PlayerFormRespondedListener;
import com.denzelcode.form.window.CustomWindowForm;
import com.denzelcode.form.window.ModalWindowForm;
import com.denzelcode.form.window.SimpleWindowForm;

import java.util.List;

public class FormAPI extends PluginBase {

    public static Thread mainThread;

    public static void init(PluginBase plugin) {
        mainThread = Thread.currentThread();

        Server.getInstance().getPluginManager().registerEvents(new PlayerFormRespondedListener(), plugin);
    }

    public static SimpleWindowForm simpleWindowForm(String title) {
        return new SimpleWindowForm(title);
    }

    public static SimpleWindowForm simpleWindowForm(String name, String title) {
        return new SimpleWindowForm(name, title);
    }

    public static SimpleWindowForm simpleWindowForm(String name, String title, String content) {
        return new SimpleWindowForm(name, title, content);
    }

    public static CustomWindowForm customWindowForm(String title) {
        return new CustomWindowForm(title);
    }

    public static CustomWindowForm customWindowForm(String name, String title) {
        return new CustomWindowForm(name, title);
    }

    public static CustomWindowForm customWindowForm(String name, String title, List<Element> content) {
        return new CustomWindowForm(name, title, content);
    }

    public static ModalWindowForm modalWindowForm(String title, String content, String acceptButtonText, String cancelButtonText) {
        return new ModalWindowForm(title, content, acceptButtonText, cancelButtonText);
    }

    public static ModalWindowForm modalWindowForm(String name, String title, String content, String acceptButtonText, String cancelButtonText) {
        return new ModalWindowForm(name, title, content, acceptButtonText, cancelButtonText);
    }

    public static void sendWindow(Player player, FormWindow window) {
        if (player == null) return;

        ServerScheduler scheduler = Server.getInstance().getScheduler();

        Task task = new Task() {
            @Override
            public void onRun(int i) {
                player.showFormWindow(window);

                scheduler.scheduleDelayedTask(new Task() {
                    @Override
                    public void onRun(int i) {
                        player.sendExperience();
                    }
                }, 20);
            }
        };

        if (Thread.currentThread() == FormAPI.mainThread) {
            task.onRun(0);
        } else {
            scheduler.scheduleTask(task);
        }
    }

    @Override
    public void onEnable() {
        init(this);
    }
}
