package com.ppioli.url.actors.actions;

public class ActionResult {
    public final boolean completed;
//    public final boolean succeded;

    public ActionResult( boolean succeded, boolean completed ) {
//        this.succeded = succeded;
        this.completed = completed;
    }

    public static ActionResult success() {
        return new ActionResult( true, true );
    }

    public static ActionResult progress() {
        return new ActionResult( true, false );
    }
}
