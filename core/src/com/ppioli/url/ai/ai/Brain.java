package com.ppioli.url.ai.ai;

import com.badlogic.ashley.core.Entity;
import com.ppioli.url.actors.actions.IAction;

import java.util.Optional;

public interface Brain {
    Optional<IAction> getAction();
    void onInterrupt();
}
