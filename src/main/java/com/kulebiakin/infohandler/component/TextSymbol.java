package com.kulebiakin.infohandler.component;

import java.util.Collections;
import java.util.List;

public class TextSymbol implements TextComponent {

    private final char symbol;

    public TextSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public void add(TextComponent component) {
        throw new UnsupportedOperationException("Cannot add component to symbol");
    }

    @Override
    public void remove(TextComponent component) {
        throw new UnsupportedOperationException("Cannot remove component from symbol");
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public TextComponentType getType() {
        return TextComponentType.SYMBOL;
    }

    public char getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextSymbol that = (TextSymbol) o;
        return symbol == that.symbol;
    }

    @Override
    public int hashCode() {
        return symbol;
    }
}
