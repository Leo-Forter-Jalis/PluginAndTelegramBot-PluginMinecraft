package com.lfj.plugin.patb.listener;

import io.papermc.paper.registry.RegistryBuilderFactory;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.DialogRegistryEntry;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import io.papermc.paper.dialog.DialogResponseView;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import io.papermc.paper.dialog.Dialog;

import io.papermc.paper.event.connection.configuration.AsyncPlayerConnectionConfigureEvent;

import java.util.List;
import java.util.function.Consumer;

public class ConnectionsPlayers implements Listener {

    @EventHandler
    public void connections(AsyncPlayerConnectionConfigureEvent e){
        ActionButton button1 = ActionButton.create(
                Component.text("TEST1"),
                null,
                100,
                DialogAction.staticAction(ClickEvent.suggestCommand(""))
        );
        ActionButton button2 = ActionButton.create(
                Component.text("TEST2"),
                null,
                100,
                DialogAction.staticAction(ClickEvent.suggestCommand(""))
        );
        DialogInput input = DialogInput.text(
                "confirm",
                100,
                Component.text("TEST3"),
                false,
                "SS",
                6,
                null
        );
        List<DialogInput> list = List.of(
                input
        );
        Dialog confirmationDialog = Dialog.create((Consumer<RegistryBuilderFactory<Dialog, ? extends DialogRegistryEntry.Builder>>)
                DialogBase.create(
                        Component.text("TITLE"),
                        Component.text("TEST"),
                        false,
                        false,
                        null,
                        List.of(
                                DialogBody.plainMessage(Component.text("TEST2"))
                        ),
                        null
                )
        );

        e.getConnection().getAudience().showDialog(confirmationDialog);
    }

}
