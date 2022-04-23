package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.entertainment.MysqlConnection;
import de.goldendeveloper.mysql.entities.Database;
import de.goldendeveloper.mysql.entities.MysqlTypes;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import org.jetbrains.annotations.NotNull;

public class GalgenGame {

    public GalgenGame(String Table) {
        Database db = Main.getMysqlConnection().getMysql().getDatabase(MysqlConnection.dbName);
        Table table = db.getTable(Table);
        if (!table.existsColumn("difficulty")) {
            table.addColumn("difficulty", MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn("active")) {
            table.addColumn("active", MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(MysqlConnection.GameErrors)) {
            table.addColumn(MysqlConnection.GameErrors, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(MysqlConnection.GalgenBuchstaben)) {
            table.addColumn(MysqlConnection.GalgenBuchstaben, MysqlTypes.VARCHAR, 50);
        }
        if (!table.existsColumn(MysqlConnection.columnGameBegriff)) {
            table.addColumn(MysqlConnection.columnGameBegriff, MysqlTypes.VARCHAR, 50);
        }
        if (table.isEmpty()) {
            fillTable(table);
        }
    }

    public void fillTable(Table table) {
        insert(table, MysqlConnection.columnGameBegriff, "Fernbedienung", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Rasierschaum", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Laderampe", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Lichterkette", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Rauhfasertapete", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Tannenbaumständer", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Siebträgermaschine", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Lampenfassung", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schneeketten", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Konfettiregen", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Geschenkpapier", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Wollschal", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schleifenbank", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Rundumleuchte", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Kugelbahn", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schaukelpferd", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Verhaltensmuster", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Handcreme", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Räuchermännchen", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Querschläger", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Adventskalender", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Brillenputztuch", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Hauptgewinn", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Wäscheklammer", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Spiegelreflexkameraame", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Pralinenschachtel", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Drehbuch", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Kinderstuhl", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Sonnenblumenöl", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Christbaumkugel", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Zungenkuss", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Ladegerät", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schreihals", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Glühwein", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Osterhase", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Teppichklopfer", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schokokringel", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Vollpfosten", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Sprudelwasser", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Kichstarter", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Eierlikör", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Neujahresgruß", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Glatteis", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Weihnachtsgans", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Lebkuchenherz", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Trostpreis", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Versuchskaninchen", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Regalboden", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Mistelzweig", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Hebamme", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Manschettenknopf", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Schneegestöber", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Renntier", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Winterstiefel", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Flussufer", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Pfauenfeder", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Ohrensessel", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Verlobungsring", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Nassrasur", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Garagentür", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Weihnachtspost", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Glasreiniger", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Intimpiercing", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Gießkanne", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Hackebeil", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Tiefkühlfach", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Treppenhaus", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Fahrradschlauch", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Kreißsaal", "easy");
        insert(table, MysqlConnection.columnGameBegriff, "Mitesser", "easy");
    }

    public static void insert(@NotNull Table table, String columnBegriff, String columnWert, String Difficulty) {
        table.insert(new Row(table, table.getDatabase())
                .with(columnBegriff, columnWert)
                .with(MysqlConnection.GalgenBuchstaben, "A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P,")
                .with(MysqlConnection.GameErrors, "0")
                .with("difficulty", Difficulty)
                .with("active", "0"));
    }
}
