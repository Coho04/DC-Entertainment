package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.MysqlTypes;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import org.jetbrains.annotations.NotNull;

public class GalgenGame {

    public GalgenGame(String Table) {
        Database db = Main.getMysql().getDatabase(Main.dbName);
        Table table = db.getTable(Table);
        if (!table.existsColumn("difficulty")) {
            table.addColumn("difficulty", MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn("active")) {
            table.addColumn("active", MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(Main.columnGameBegriff)) {
            table.addColumn(Main.columnGameBegriff, MysqlTypes.VARCHAR, 50);
        }
        if (table.isEmpty()) {
            fillTable(table);
        }
    }

    public void fillTable(Table table) {
        insert(table, Main.columnGameBegriff, "Fernbedienung", "easy");
        insert(table, Main.columnGameBegriff, "Rasierschaum", "easy");
        insert(table, Main.columnGameBegriff, "Laderampe", "easy");
        insert(table, Main.columnGameBegriff, "Lichterkette", "easy");
        insert(table, Main.columnGameBegriff, "Rauhfasertapete", "easy");
        insert(table, Main.columnGameBegriff, "Tannenbaumständer", "easy");
        insert(table, Main.columnGameBegriff, "Siebträgermaschine", "easy");
        insert(table, Main.columnGameBegriff, "Lampenfassung", "easy");
        insert(table, Main.columnGameBegriff, "Schneeketten", "easy");
        insert(table, Main.columnGameBegriff, "Konfettiregen", "easy");
        insert(table, Main.columnGameBegriff, "Geschenkpapier", "easy");
        insert(table, Main.columnGameBegriff, "Wollschal", "easy");
        insert(table, Main.columnGameBegriff, "Schleifenbank", "easy");
        insert(table, Main.columnGameBegriff, "Rundumleuchte", "easy");
        insert(table, Main.columnGameBegriff, "Kugelbahn", "easy");
        insert(table, Main.columnGameBegriff, "Schaukelpferd", "easy");
        insert(table, Main.columnGameBegriff, "Verhaltensmuster", "easy");
        insert(table, Main.columnGameBegriff, "Handcreme", "easy");
        insert(table, Main.columnGameBegriff, "Räuchermännchen", "easy");
        insert(table, Main.columnGameBegriff, "Querschläger", "easy");
        insert(table, Main.columnGameBegriff, "Adventskalender", "easy");
        insert(table, Main.columnGameBegriff, "Brillenputztuch", "easy");
        insert(table, Main.columnGameBegriff, "Hauptgewinn", "easy");
        insert(table, Main.columnGameBegriff, "Wäscheklammer", "easy");
        insert(table, Main.columnGameBegriff, "Spiegelreflexkameraame", "easy");
        insert(table, Main.columnGameBegriff, "Pralinenschachtel", "easy");
        insert(table, Main.columnGameBegriff, "Drehbuch", "easy");
        insert(table, Main.columnGameBegriff, "Kinderstuhl", "easy");
        insert(table, Main.columnGameBegriff, "Sonnenblumenöl", "easy");
        insert(table, Main.columnGameBegriff, "Christbaumkugel", "easy");
        insert(table, Main.columnGameBegriff, "Zungenkuss", "easy");
        insert(table, Main.columnGameBegriff, "Ladegerät", "easy");
        insert(table, Main.columnGameBegriff, "Schreihals", "easy");
        insert(table, Main.columnGameBegriff, "Glühwein", "easy");
        insert(table, Main.columnGameBegriff, "Osterhase", "easy");
        insert(table, Main.columnGameBegriff, "Teppichklopfer", "easy");
        insert(table, Main.columnGameBegriff, "Schokokringel", "easy");
        insert(table, Main.columnGameBegriff, "Vollpfosten", "easy");
        insert(table, Main.columnGameBegriff, "Sprudelwasser", "easy");
        insert(table, Main.columnGameBegriff, "Kichstarter", "easy");
        insert(table, Main.columnGameBegriff, "Eierlikör", "easy");
        insert(table, Main.columnGameBegriff, "Neujahresgruß", "easy");
        insert(table, Main.columnGameBegriff, "Glatteis", "easy");
        insert(table, Main.columnGameBegriff, "Weihnachtsgans", "easy");
        insert(table, Main.columnGameBegriff, "Lebkuchenherz", "easy");
        insert(table, Main.columnGameBegriff, "Trostpreis", "easy");
        insert(table, Main.columnGameBegriff, "Versuchskaninchen", "easy");
        insert(table, Main.columnGameBegriff, "Regalboden", "easy");
        insert(table, Main.columnGameBegriff, "Mistelzweig", "easy");
        insert(table, Main.columnGameBegriff, "Hebamme", "easy");
        insert(table, Main.columnGameBegriff, "Manschettenknopf", "easy");
        insert(table, Main.columnGameBegriff, "Schneegestöber", "easy");
        insert(table, Main.columnGameBegriff, "Renntier", "easy");
        insert(table, Main.columnGameBegriff, "Winterstiefel", "easy");
        insert(table, Main.columnGameBegriff, "Flussufer", "easy");
        insert(table, Main.columnGameBegriff, "Pfauenfeder", "easy");
        insert(table, Main.columnGameBegriff, "Ohrensessel", "easy");
        insert(table, Main.columnGameBegriff, "Verlobungsring", "easy");
        insert(table, Main.columnGameBegriff, "Nassrasur", "easy");
        insert(table, Main.columnGameBegriff, "Garagentür", "easy");
        insert(table, Main.columnGameBegriff, "Weihnachtspost", "easy");
        insert(table, Main.columnGameBegriff, "Glasreiniger", "easy");
        insert(table, Main.columnGameBegriff, "Intimpiercing", "easy");
        insert(table, Main.columnGameBegriff, "Gießkanne", "easy");
        insert(table, Main.columnGameBegriff, "Hackebeil", "easy");
        insert(table, Main.columnGameBegriff, "Tiefkühlfach", "easy");
        insert(table, Main.columnGameBegriff, "Treppenhaus", "easy");
        insert(table, Main.columnGameBegriff, "Fahrradschlauch", "easy");
        insert(table, Main.columnGameBegriff, "Kreißsaal", "easy");
        insert(table, Main.columnGameBegriff, "Mitesser", "easy");
    }

    public static void insert(@NotNull Table table, String columnBegriff, String columnWert, String Difficulty) {
        table.insert(new Row(table, table.getDatabase())
                .with(columnBegriff, columnWert)
                .with("difficulty", Difficulty)
                .with("active", ""));
    }
}
