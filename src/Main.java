import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        {
            ExtremeString string = new ExtremeString("You would not believe your eyes");
            System.out.println("DisEmvowelment:");
            System.out.println("\t" + string.disEmvowelment());
        }

        {
            ExtremeString string = new ExtremeString("You would not believe your eyes");
            System.out.println("Pig Latin:");
            System.out.println("\t" + string.toPigLatin());
        }

        {
            System.out.println("Lipogram:");
            ExtremeString string = new ExtremeString(
                    "You would not believe your eyes,\n" +
                    "If ten million fireflies\n" +
                    "Lit up the world as I fell asleep\n" +
                    "'Cause they'd fill the open air\n" +
                    "And leave teardrops everywhere\n" +
                    "You'd think me rude, but I would just stand and stare");
            System.out.println("\t" + string.toLipogram('U'));
        }
    }

}
