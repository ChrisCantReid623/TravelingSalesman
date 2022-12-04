import java.util.*;

public class Dice {
    public static void main(String[] args) {
        diceSum(3, 9);
    }

    public static void diceRoll(int numDice) {
        diceRoll(numDice, new ArrayList<Integer>());
    }

    private static void diceRoll(int numDice, List<Integer> rolls) {
        if (numDice == 0) {
            System.out.println(rolls);
        }
        else {
            for (int i = 1; i <= 6; i++) {
                rolls.add(i);
                diceRoll(numDice - 1, rolls);
                rolls.remove(rolls.size() - 1);
            }
        }
    }

    public static void diceSum(int numDice, int desired_sum) {
        diceSum(numDice, desired_sum, new ArrayList<Integer>(), 0);
    }

    private static void diceSum(int numDice, int desired_sum, List<Integer> rolls, int curr_sum) {
        if (numDice == 0) {
            if (curr_sum == desired_sum) {
                System.out.println(rolls);
            }
        }
        else if (curr_sum + numDice <= desired_sum && curr_sum + numDice *6 >= desired_sum) {
            for (int i = 1; i <=6; i++) {
                rolls.add(i);
                diceSum(numDice -1, desired_sum, rolls, curr_sum + i);
                rolls.remove(rolls.size() - 1);
            }
        }
    }
}