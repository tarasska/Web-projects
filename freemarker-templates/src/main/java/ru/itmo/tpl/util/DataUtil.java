package ru.itmo.tpl.util;

import ru.itmo.tpl.model.ComponentURI;
import ru.itmo.tpl.model.Post;
import ru.itmo.tpl.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirayanov", "Mikhail Mirzayanov", User.HandleColor.RED),
            new User(2, "tourist", "Genady Korotkevich", User.HandleColor.RED),
            new User(3, "emusk", "Elon Musk", User.HandleColor.GREEN),
            new User(5, "pashka", "Pavel Mavrin", User.HandleColor.RED),
            new User(7, "geranazavr555", "Georgiy Nazarov", User.HandleColor.BLUE),
            new User(11, "cannon147", "Erofey Bashunov", User.HandleColor.BLUE),
            new User(14, "taras_ska", "Taras Skazhenik", User.HandleColor.BLUE)
    );

    private static final List<ComponentURI> menuURI = Arrays.asList(
            new ComponentURI("/index", "index"),
            new ComponentURI("/users", "users"),
            new ComponentURI("/misc/help", "help")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "First Post", "It is the first post on our website!", 1),
            new Post(2,
                    "A typical American",
                    "What makes every American a typical one is a desire to get a well-paid job that will cover " +
                            "their credit card. A credit card is an indispensable part of life in America. In other words, " +
                            "any American knows that how he or she handles their credit card or cards, either will help " +
                            "them or haunt them for years.\n" +
                            "\n" +
                            "In the U.S. getting a card isn’t as hard as it used to be. Some companies now mail " +
                            "applications to high school students, rather than waiting for them to get into college. " +
                            "At their best credit cards allow their owners to reserve hotel rooms, rent cars and finance " +
                            "larger purchases over several months. At their worst, cards allow people with poor money " +
                            "management skills to get into a high-interest debt.\n" +
                            "\n" +
                            "For those who are deep in credit card debt, there are some Credit Services agencies that " +
                            "offer anyone in America both online or telephone, and face-to-face counseling. The counselors " +
                            "may propose a debt management plan. The plan includes lower interest payments for clients and " +
                            "setting up a pay off timetable. The agencies’ average client makes about $32,000 a year and " +
                            "has more than $16,000 debt in the program. Usually the agencies charge zero up to $30 monthly " +
                            "to manage the debt service plan.\n" +
                            "\n" +
                            "Credit Services counselors advise, that if an American gets divorced, he or she may eliminate " +
                            "all joint debts by paying them off or transferring debt into a one-name account. Even in happy " +
                            "marriages, the agency advises that the husband and wife should each have a credit card in their" +
                            " name only to establish separate credit histories.\n" +
                            "\n" +
                            "Once debts have been repaid, an American can re-establish his/her good credit by applying for " +
                            "a secured credit card and paying the balance off regularly. A store credit card usually is " +
                            "the next step, followed by applying for a major credit card such as a Visa or MasterCard.",
                    1),
            new Post(10,
                    "Codeforces Round #592 (Div. 2)",
                    "Привет, Codeforces!\n" +
                            "\n" +
                            "13 октября 2019 года в 12:05 MSK состоится Codeforces Round #592 (Div. 2). Обратите " +
                            "внимание на необычное время начала раунда!\n" +
                            "\n" +
                            "Раунд будет рейтинговым для участников второго дивизиона (с рейтингом менее 2100). " +
                            "Условия будут доступны как на русском, так и на английском языках.\n" +
                            "\n" +
                            "Этот раунд проводится по задачам регионального этапа Всероссийской командной олимпиады" +
                            " школьников по программированию 2019, проходящего в Саратове. Задачи вместе со мной " +
                            "придумывали и готовили Иван BledDest Андросов и Владимир Vovuh Петров.\n" +
                            "\n" +
                            "Хотелось бы сказать большое спасибо Ивану isaf27 Сафонову за помощь в подготовке задач, " +
                            "Михаилу MikeMirzayanov Мирзаянову за замечательные системы Codeforces и Polygon, а " +
                            "также Ивану CaseRuten Худошину, Ивану Ivan19981305 Георгиеву, Леониду Peinot Миронову, " +
                            "Антону anon20016 Лебедеву, Ксении Pavlova Павловой и Дмитрию dmitrii.krasnihin Краснихину " +
                            "за прорешивание задач.\n" +
                            "\n" +
                            "Участникам будет предложено семь задач и два часа на их решение. Разбалловка будет объявлена позднее.",
                    11),
            new Post(69,
                    "Short Post Example",
                    "Hi, my name is Taras Skazhenik and this is my first post on codeforces!",
                    14),
            new Post(366,
                    "Story",
                    "Исто́рия ру́сского литерату́рного языка́ — формирование и преобразование русского языка, " +
                            "используемого в литературных произведениях. Старейшие из сохранившихся литературных " +
                            "памятников предка языка — древнерусского языка датируются XI веком. В XVIII—XIX веках " +
                            "этот процесс происходил на фоне противопоставления русского языка, на котором говорил народ, " +
                            "французскому — языку дворян. Классики русской литературы активно исследовали возможности " +
                            "русского языка и были новаторами многих языковых форм. Они подчеркивали богатство русского " +
                            "языка и часто указывали на его преимущества по сравнению с языками иностранными. " +
                            "На почве таких сравнений неоднократно возникали диспуты, например, споры между западниками" +
                            " и славянофилами. В советские времена подчёркивалось, что русский язык — язык строителей " +
                            "коммунизма. Изменение норм русского литературного языка продолжается и в настоящее время.\n",
                    14)
    );

    private static List<User> getUsers() {
        return USERS;
    }

    private static List<ComponentURI> getURI() {
        return menuURI;
    }

    private static List<Post> getPosts() { return POSTS; }

    public static void putData(Map<String, Object> data) {
        data.put("users", getUsers());
        data.put("menuURI", getURI());
        data.put("posts", getPosts());

        for (User user : getUsers()) {
            if (data.get("logged_user_id") != null && user.getId() == (long) data.get("logged_user_id")) {
                data.put("user", user);
            }
        }
    }

}
