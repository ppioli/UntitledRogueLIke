package com.ppioli.url.ai;

public interface BoardCell {
    boolean isBlocked();
    boolean isOpaque();
    boolean isAttackable();
}
