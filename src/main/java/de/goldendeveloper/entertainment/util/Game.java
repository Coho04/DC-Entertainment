package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import org.jetbrains.annotations.NotNull;

public class Game {

    public Game(@NotNull Table table) {
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Valorant"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Minecraft"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Call of Duty: Black Ops Cold War"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Call of Duty: Black Ops 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Call of Duty: Warzone"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Call of Duty: Modern Warfare"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "League of Legends"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Apex Legens"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Grand Theft Auto 5"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Shellshock Live"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Remant from the Ashes"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Rougue Company"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Pico Park"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Raft"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Rocket League"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Split Gate"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Fornite"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Orcs Must Die! 3"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Immortal Fenxy Rising"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Stranded Deep"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Among Us"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Watch Dogs 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Satisfactory"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Kingdom new Lands"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Counter Strike"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "FarCry 5"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "FarCry 6"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Mud Runner"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Destiny 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Battelfront 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "StarCraft 1"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "StarCraft 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Age of Empire 2"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Subnautica"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Subnautica Below Zero"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Sea of Thives"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Supraland"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Terraria"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Stone.world"));
        table.insert(new Row(table, table.getDatabase()).with(Main.columnName, "Overcooked"));
    }
}
