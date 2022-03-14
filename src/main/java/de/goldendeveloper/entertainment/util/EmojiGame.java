package de.goldendeveloper.entertainment.util;

import de.goldendeveloper.entertainment.Main;
import de.goldendeveloper.mysql.entities.Row;
import de.goldendeveloper.mysql.entities.Table;
import org.jetbrains.annotations.NotNull;

public class EmojiGame {

    public EmojiGame(Table table) {
        insert(table, Main.columnGameBegriff, "Fernbedienung", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Rasierschaum", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Laderampe", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Lichterkette", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Rauhfasertapete", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Tannenbaumständer", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Siebträgermaschine", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Lampenfassung", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schneeketten", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Konfettiregen", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Geschenkpapier", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Wollschal", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schleifenbank", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Rundumleuchte", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Kugelbahn", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schaukelpferd", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Verhaltensmuster", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Handcreme", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Räuchermännchen", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Querschläger", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Adventskalender", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Brillenputztuch", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Hauptgewinn", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Wäscheklammer", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Spiegelreflexkamera", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Pralinenschachtel", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Drehbuch", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Kinderstuhl", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Sonnenblumenöl", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Christbaumkugel", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Zungenkuss", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Ladegerät", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schreihals", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Glühwein", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Osterhase", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Teppichklopfer", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schokokringel", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Vollpfosten", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Sprudelwasser", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Kichstarter", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Eierlikör", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Neujahresgruß", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Glatteis", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Weihnachtsgans", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Lebkuchenherz", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Trostpreis", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Versuchskaninchen", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Regalboden", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Mistelzweig", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Hebamme", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Manschettenknopf", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Schneegestöber", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Renntier", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Winterstiefel", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Flussufer", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Pfauenfeder", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Ohrensessel", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Verlobungsring", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Nassrasur", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Garagentür", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Weihnachtspost", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Glasreiniger", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Intimpiercing", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Gießkanne", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Hackebeil", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Tiefkühlfach", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Treppenhaus", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Fahrradschlauch", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Kreißsaal", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
        insert(table, Main.columnGameBegriff, "Mitesser", Main.GameDifficulty, "easy", Main.GameEmojiOne, "", Main.GameEmojiTwo, "");
    }

    public static void insert(@NotNull Table table, String columnBegriff, String columnWert, String Difficulty, String GameDifficultyWert, String GameEmojiOne, String GameEmojiOneWert, String GameEmojiTwo, String GameEmojiTwoWert) {
        table.insert(new Row(table, table.getDatabase()).with(columnBegriff, columnWert).with(Difficulty, GameDifficultyWert).with(GameEmojiOne, GameEmojiOneWert).with(GameEmojiTwo, GameEmojiTwoWert));
    }
}
