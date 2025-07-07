package com.kulebiakin.infohandler.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TextComposite implements TextComponent {

    private final TextComponentType type;
    private final List<TextComponent> children = new ArrayList<>();

    public TextComposite(TextComponentType type) {
        this.type = type;
    }

    @Override
    public void add(TextComponent component) {
        children.add(component);
    }

    @Override
    public void remove(TextComponent component) {
        children.remove(component);
    }

    @Override
    public List<TextComponent> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public TextComponentType getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String delimiter = "";

        switch (type) {
            case TEXT:
                delimiter = "\n";
                break;
            case PARAGRAPH:
                delimiter = " ";
                break;
            case SENTENCE:
                delimiter = " ";
                break;
            case LEXEME:
                delimiter = "";
                break;
            case WORD:
                delimiter = "";
                break;
            case SYMBOL:
                delimiter = "";
                break;
        }

        for (int i = 0; i < children.size(); i++) {
            sb.append(children.get(i).toString());
            if (i != children.size() - 1) {
                sb.append(delimiter);
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextComposite that = (TextComposite) o;
        return type == that.type && children.equals(that.children);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(type);
        result = 31 * result + children.hashCode();
        return result;
    }
}
