package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import org.jetbrains.annotations.NotNull;

public class EightBall {

    public EightBall(@NotNull Table table) {
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Auf jeden fall!"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Ja, sicher."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Ganz sicher, dude!"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Vielleicht..."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Nah..."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Nein, wirklich, nicht..."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Es ist sicher."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Es ist ganz bestimmt so."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Ohne jeden Zweifel."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Ja - ganz bestimmt."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Sie können sich darauf verlassen."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "So wie ich es sehe, ja."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Höchstwahrscheinlich."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Gute Aussichten."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Ja."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Die Zeichen stehen auf Ja."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Antwort undeutlich, versuchen Sie es noch einmal."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Fragen Sie später noch einmal."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Besser nicht jetzt sagen."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Kann ich jetzt nicht vorhersagen."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Konzentrieren Sie sich und fragen Sie noch einmal."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Verlassen Sie sich nicht darauf."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Meine Antwort ist nein."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Meine Quellen sagen nein."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Aussichten nicht so gut."));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Sehr zweifelhaft."));
    }
}
