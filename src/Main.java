import model.Player;
import org.apache.log4j.Logger;

import java.util.List;

public class Main {

    private final static String titleMessage = "-= Игра крестики-нолики =-\n";
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {

        // Алгоритм работы программы
        /*
            1. Приветственное слово. (Название игры)
            2. Выбор пользователя (чем будет играть, размер поля)
            3. Игра
                3.1 Рисуем поле
                3.2 Запрашиваем координаты у игрока
                3.3 Проверяем что по указанным координатам возможно поставить фишку
                3.4 Если это возможно ставим фишку
                3.5 Рисуем поле
                3.6 Проверяем на победу/ничья. Если победа ставим имя победителям и выходим на пункт 4
                3.7 Передаем ход второму игроку
            4. Конец игры. Результаты
         */
        Player player1 = new Player(true);
        Player player2 = new Player(false);
        List<Player> listPlayers = List.of(player1, player2);
        Game game = new Game(titleMessage);
        game.play(listPlayers);
    }


}
