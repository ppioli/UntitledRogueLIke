package com.ppioli.url.input;

import com.ppioli.url.input.command.Command;

import java.util.Optional;

public interface InputAdapter {

    Optional<Command> getCommandAndClear();
}
