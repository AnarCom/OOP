package com.example.game.field;

public class DeadException extends RuntimeException{
    public DeadException() {
        super("Snake is dead");
    }
}
