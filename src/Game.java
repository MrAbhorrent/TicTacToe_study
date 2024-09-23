import model.PlaySymbols;
import model.Player;
import org.apache.log4j.Logger;
import view.ConsoleView;
import view.View;

import java.awt.*;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

public class Game {

    private final static String strInfoAboutStep = "=> Ход игрока %s... \n";
    private final static String strInfoWinUser = "Победил игрок - %s\n";
    private final static String strDeathDead = "Игровое поле заполнено. Продолжение игры невозможно\n";
    private final String titleMessage;
    private String strInfoGameOver;
    private View view;
    private static final Logger logger = Logger.getLogger(Game.class);

    public Game(String strInfoGame) {
        this.titleMessage = strInfoGame;
    }

    public void play(List<Player> players){
        PlayingField playingField = new PlayingField();
        view = new ConsoleView(titleMessage);
        //view = new WindowView(titleMessage, playingField.getSizeField());

        int userChoice = getUserChoice();
        switch (userChoice) {
            case 1 -> {
                players.get(0).setSymbol(PlaySymbols.SYMBOL_0.getValue());
                players.get(1).setSymbol(PlaySymbols.SYMBOL_X.getValue());
            }
            case 2 -> {
                players.get(0).setSymbol(PlaySymbols.SYMBOL_X.getValue());
                players.get(1).setSymbol(PlaySymbols.SYMBOL_0.getValue());
            }
        }

        logger.info(
                String.format(
                        "Log => Начало игры =========\nИгроки: %s",
                        players.toString())
        );
        boolean flagEndGame = false;
        view.drawPlayingField(playingField.getPlayingField());
        do {
            for (Player player : players) {
                view.outputMessage(String.format(strInfoAboutStep, player.getName()));
                Point cell = turnPlayer(view, playingField, player);
                view.drawPlayingField(playingField.getPlayingField());

                if (playingField.checkWin(cell, player.getSymbol())) {
                    strInfoGameOver = String.format(strInfoWinUser, player.getName());
                    flagEndGame = true;
                } else if (playingField.isFull()) {
                    strInfoGameOver = strDeathDead;
                    flagEndGame = true;
                }
            }
        } while (!flagEndGame);
        view.outputMessage(strInfoGameOver);
    }

    private static Point turnPlayer(View view, PlayingField playingField, Player player) {
        Point cell;
        int selectRow, selectColumn;
        do {
            if (player.isHuman()) {
                view.outputMessage("Введите номер строки: ");
                selectRow = view.inputNumber() - 1;
                view.outputMessage("Введите номер столбца: ");
                selectColumn = view.inputNumber() - 1;
            } else {
//                Random random = new Random();
//                selectRow = random.nextInt(playingField.getSizeField());
//                selectColumn = random.nextInt(playingField.getSizeField());
                cell = thinkingAICell(player, playingField);
                selectRow = cell.x;
                selectColumn = cell.y;
            }
            logger.info(
                    String.format(
                            "Log => %s Проба координат: [%d, %d]",
                            player.getName(), selectRow , selectColumn)
            );
            if (playingField.setSign(player.getSymbol(), selectRow, selectColumn)) {
                logger.info(
                        String.format(
                                "Log => %s Координаты приняты: [%d, %d]",
                                player.getName(), selectRow , selectColumn)
                );
                cell = new Point(selectRow, selectColumn);
                return cell;
            }
            if (player.isHuman()) {
                view.outputMessage("Введена не корректная позиция символа. Попробуйте снова.\n");
            }
        } while (true);
    }

    private static Point thinkingAICell(Player player, PlayingField playingField) {
        Random random = new Random();
        Point cell = new Point();
        int aiLevel = 2;
        switch (aiLevel) {
            case 1 -> {
                cell.x = random.nextInt(playingField.getSizeField());
                cell.y = random.nextInt(playingField.getSizeField());
                player.saveTurn(cell);
            }
            case 2 -> {
                do {
                    cell.x = random.nextInt(playingField.getSizeField());
                    cell.y = random.nextInt(playingField.getSizeField());
                } while (player.getHistoryMap().containsKey(cell));
                player.saveTurn(cell);
            }
            // усложняем логику выбора ячейки для робота
        }
        return cell;
    }

    private int getUserChoice() {
        String strSelectSymbol = "Выберете каким символом вы будете играть";
        int selectUserChoice;
        do {
            try {
                String strInfoChoice = String.format("%s:\n1. %s\n2. %s\n", strSelectSymbol, PlaySymbols.SYMBOL_0.getValue(), PlaySymbols.SYMBOL_X.getValue());
                view.outputMessage(strInfoChoice);
                selectUserChoice = view.inputNumber();
                if (selectUserChoice == 1 || selectUserChoice == 2) {
                    return selectUserChoice;
                }
            } catch (InputMismatchException | NumberFormatException exception) {
                view.outputMessage("Введено не число. Введите 1 или 2.");
            }
        } while (true);
    }
}


