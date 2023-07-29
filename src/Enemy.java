import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Player {

    public Enemy() {super("");

        this.name = (names[new Random().nextInt(names.length)]+
                ThreadLocalRandom.current().nextInt(100));

    }

    private String[] names = {"Ali", "Mohammad", "Mahdi", "Reza", "Musa", "Isa", "Hassan", "Hossein", "Ahmad", "Alireza", "narges", "Fatemeh", "Zahra",
            "Mhmdreza", "Amir", "Sajad", "Seyed", "Parya", "Nima", "Shahram", "Armin", "Aiden", "Parsa", "ida", "Atousa", "Mahsa", "Nika",
            "Aria", "Bahar", "Shayan", "Saman", "Aryan", "Shahab", "Nima", "Pezhman", "Nahid", "Niki", "Yasmin", "Raha", "Arman", "Pegah",
            "Behzad", "Hanieh", "Kamran", "Taraneh", "Shervin", "Maral", "Farzad", "Shima","Babak", "Tina", "Keyvan", "Tara", "Navid", "Melika",
            "Donya",  "Farnaz", "Ramin", "Behrang", "Anousha", "Hooman", "Sara", "Shadi", "Kian", "Roya", "Hadi",};



}
