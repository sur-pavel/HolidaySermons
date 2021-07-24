package com.sur_pavel.holidaysermons;

import java.util.Arrays;
import java.util.List;

public class Data
{
    private static Data instance;
    private Data(){}
    public static Data getInstance(){
        if(instance == null)
        {
            return new Data();
        }
        return instance;
    }
    public static List<String> fathers = Arrays.asList(
            "Иоанн Златоуст",
            "Василий Великий",
            "Григорий Богослов",
            "Феофан Затворник",
            "Иннокентий Херсонский",
            "Иоанн Кронштадтский",
            "Серафим Чичагов",
            "Николай Сербский",
            "Лука Войно-Ясенецкий",
            "Филарет Московский",
            "Филарет Амфитеатров",
            "Алексей Мечев",
            "Иоанн Шанхайский"
    );
    public static List<String> holidays = Arrays.asList(
            "Рождество",
            "Крещение",
            "Обрезание",
            "Сретение",
            "Великий пост",
            "Благовещение",
            "Вход Господень в Иерусалим",
            "Пасха",
            "Пятидесятница",
            "Святой Дух",
            "Рождество Иоанна Предтечи",
            "Петра и Павла",
            "Сергий Радонежский",
            "Казанская",
            "Преображение",
            "Успение",
            "Рождество Богородицы",
            "Воздвижение",
            "Покров",
            "Введение"
    );
    public String holiday;
}
