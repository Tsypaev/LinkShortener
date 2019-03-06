[![Build Status](https://travis-ci.org/Tsypaev/link-shortener.svg?branch=master)](https://travis-ci.org/Tsypaev/link-shortener)
# link-shortener
Для старта проекта нужно:
1) Распаковать проект;
2) Открыть его в Intellige Idea(Данный проект нужно открывать как maven проект);
3) Папку `main.java` нужно пометить как `Sources Root`(Если это уже не сделано);
4) Папку `main.test` нужно пометить как `Test Sources Root`(Если это уже не сделано);
5) Для формирования jar файла нужно в корне проекта воспользоваться командой: `mvn package`;(Желательно предварительно сделать команду `mvn clean`);
6) Для запуска сервиса нужно в месте, где сформировался jar файл применить команду: `java -jar <название сервиса>.jar`. Проект запустится с настройками по умолчанию.

* Если нужно запустить проект со своими настройками следует переименовать файл `application.properties.sample` в `application.properties` и выставить в нем те значения параметров, которые нужны. Существующий файл же следует переименовать в `application.properties.sample`.
