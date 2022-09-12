import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));
    }

    public static String calc(String input) throws Exception {

        //Определяем арифметическую операцию
        String[] operations = {"+", "-","*", "/"};
        String[] regexOperations = {"\\+", "-","\\*", "/"};

        int operationIndex=-1;
        for (int i = 0; i <operations.length; i++) {
            if(input.contains(operations[i])){
                operationIndex = i;
                break;
            }
        }

        //Разделяем выражение на 2 части, если частей больше - выкидываем исключение
        String[] arrayOfNumbers = input.split(regexOperations[operationIndex]);
        if (arrayOfNumbers.length > 2) {
            throw new Exception();
        }

        boolean flag = false;
        int number;
        int num1, num2;

        //Проверка на работу с арабскими или римскими цифрами одновременно, если вводится арабское и римское число - выкидываем исключение
        if(isRomeNumbers(arrayOfNumbers[0].trim()) && isRomeNumbers(arrayOfNumbers[1].trim())){
            num1 = romeToArab(arrayOfNumbers[0].trim());
            num2 = romeToArab(arrayOfNumbers[1].trim());
            flag = true;
        }
        else if(isArabNumbers(arrayOfNumbers[0].trim()) && isArabNumbers(arrayOfNumbers[1].trim())){
            num1 = Integer.parseInt(arrayOfNumbers[0].trim());
            num2 = Integer.parseInt(arrayOfNumbers[1].trim());
        }
        else throw new Exception();

        //Вычисляем результат выражения, если ввести неизвестную аривметическую операцию - выкидываем исключение
        switch (operations[operationIndex]) {
            case "+" -> number = num1 + num2;
            case "-" -> number = num1 - num2;
            case "*" -> number = num1 * num2;
            case "/" -> number = Math.round(num1 / num2);
            default -> throw new Exception();
        }

        if(flag){
            return arabToRome(number);
        }
        else return Integer.toString(number);
    }

    //Проверяем является ли число арабским, а так же больше 0 и меньше 10
    public static boolean isArabNumbers(String str) {
        return Integer.parseInt(str) > 0 && Integer.parseInt(str) <= 10;
    }

    //Проверяем является ли число римским, а так же больше 0 и меньше 10
    public static boolean isRomeNumbers(String str){
            boolean bool=false;
            for (int i = 0; i <= 9; i++) {
                RomeNumber romeNumber=RomeNumber.values()[i];
                if (romeNumber.name().equals(str)) {
                    bool = true;
                    break;
                }
            }
            return bool;
        }

    //Переводим римские числа в арабские
    public static int romeToArab(String str) {
        int romeNumber = 0;
            for (int i = 0; i <= 9; i++) {
                RomeNumber romeNumbers = RomeNumber.values()[i];
                if (romeNumbers.name().equals(str)) {
                    romeNumber = romeNumbers.getRomeToArab();
                    break;
                }
            }
        return romeNumber;
    }

    //Переводим арабские числа в римские
    public static String arabToRome(int number) throws Exception {

        if(number<=0){
            throw new Exception();
        }

        //Создаем 2 списка и заполняем их значениями из RomeNumber
        List<Integer> arabNumbers = new ArrayList<>();
        for (int i = 0; i <RomeNumber.values().length; i++) {
           arabNumbers.add(RomeNumber.values()[i].getRomeToArab());
        }
        List<String> romeNumbers = new ArrayList<>();
        for (Enum enums : RomeNumber.values()){
            romeNumbers.add(enums.name());
        }

        //Берем самое большое число из RomeNumber и сравниваем его с number, пока number больше либо равен
        //наибольшему числу из RomeNumber конкатенируем римские цифры в result
        StringBuilder result = new StringBuilder();
        int end = arabNumbers.size();
        for (int i = end-1; i >=0; i--) {
           while (number >= arabNumbers.get(i)){
               number -= arabNumbers.get(i);
               result.append(romeNumbers.get(i));
        }
    }
        return result.toString();
    }
}