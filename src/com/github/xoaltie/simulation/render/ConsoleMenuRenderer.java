package com.github.xoaltie.simulation.render;

public class ConsoleMenuRenderer implements MenuRenderer {
    @Override
    public void printStartMenu() {
        System.out.println("Добро пожаловать в Симуляцию!");
        System.out.println("Команды:\n1. Начать симуляцию\n2. Выход");
        System.out.print("Введите команду: ");
    }

    @Override
    public void printPauseMenu() {
        System.out.println("Команды:\n1. Возобновить симуляцию\n2. Остановить симуляцию");
        System.out.print("Введите команду: ");
    }

    @Override
    public void printInvalidInput() {
        System.out.println("Некорректная команда!");
    }
}
