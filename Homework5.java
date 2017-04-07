// John Arena CSC 221 Homework 5 3:30-4:45pm Due 12/5/16
package homework.pkg5;
import java.io.IOException;

public class Homework5 {

    public static void main(String[] args) throws IOException { // IOException for readfile and close file
        HistogramLetters h1 = new HistogramLetters(); // New histogram object
        h1.openFile(); // Calls these methods
        h1.readFile();
        h1.closeFile();
        h1.drawPieChart();
      
    }
}
