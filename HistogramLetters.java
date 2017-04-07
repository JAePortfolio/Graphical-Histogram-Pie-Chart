// John Arena CSC 221 Homework 5 3:30-4:45pm Due 12/5/16
package homework.pkg5;
// Lots of imports
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HistogramLetters { // Variables
    //private  Scanner input;
    private  BufferedReader bufferReader;
    private  int[] histogram = new int[26];
    private  double[] probability = new double[26];
    private  double[] arc_angles = new double[26];
    private  char[] alphabet = new char[26];
    private  Color colorGenerator;
    private  int n, filtered_letters_length;
    
    public HistogramLetters(){ // Constructor
    }
    
    public  void openFile(){ // File opener
        try {
            //input = new Scanner(Paths.get("angleTest.txt"));
            bufferReader = new BufferedReader(new FileReader("xWords.txt"),15000); // Reads text from a character-input stream, buffering characters so as to provide for the efficient reading of characters, arrays, and lines
        }
        //catch(IOException ioException) {
        catch(FileNotFoundException foundException){ // Catch statement if the file specified not found
            System.err.println("Error! File not found!");
            System.exit(1);
        }
    }
    
    public  void readFile() throws FileNotFoundException{ // For some reason I had to suddenly add a throws here next to the method declaration, I believe its cause of BufferedReader
        try {
            StringBuilder stringBuilder = new StringBuilder(); // Constructs a string builder with no characters in it and an initial capacity of 16 characters.
            String input = bufferReader.readLine(); // Reads the lines from the buffer reader
            String filtered_letters = ""; // Starts a string that has nothing in it
            while(input != null){ // While there's input
                //System.out.println(input);
                stringBuilder.append(input); // Append is similar to an input method, but append specifically always ats it to the end, where input you can specify it 
                input = bufferReader.readLine(); // Read
                /*while (input.hasNext()){ // Explained at the end of file
                  String char_read = input.nextLine();
                  filtered_letters += char_read.replaceAll("[^a-zA-Z]","").toLowerCase();*/
            }
            String char_read = stringBuilder.toString();
            filtered_letters = char_read.replaceAll("[^a-zA-Z]","").toLowerCase();
            filtered_letters_length = filtered_letters.length(); // Assign the length of the filtered letters
            System.out.println(char_read);
            for(char c='a'; c<='z'; c++) { // Looping through every letter of the alphabet, lowercase that is
                int count = 0; // Initialize count to 0
                alphabet[(int) (c) - 97] = c; // This fills up a character array with letters, explained at the end of the file
                for(int j = 0; j<filtered_letters_length; j++){ // Looping through the length of the filtered letters
                    if(c == filtered_letters.charAt(j)){ // If c is equal to the character at a certain position in the spring...
                        count++; // ...Count 1 each time a match is found
                    }
                }
                histogram[(int) (c) - 97] = count; // Fills up the histogram array, The ASCII codes for the lowercase letters, 97 because its = a, so the first letter would be a which has value of 97-97=0, then 98-97=1, etc
                Calculations((int) c - 97,count,filtered_letters_length); // This passes the index, how many of the matches found, and the length to a function I made to do some calculations for a few things needed for the program
                //i++; 
            }
            //System.out.println(Arrays.toString(histogram)); Tests if everything working..
            //System.out.println(filtered_letters);
        }
        catch(NoSuchElementException elementException) { // Catch if the file has an improper format
            System.err.println("File improperly formatted! Program will now close");
            System.exit(1);
        }
        catch(IllegalStateException stateException) { // Signals when a method invoked/called at a wrong/inappropriate time
            System.err.println("File cannot be read! Program will now close");
        } catch (IOException ex) { // I believe bufferreader needed this, java added it automatically
            Logger.getLogger(HistogramLetters.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public  void closeFile() throws IOException{ // Exception added by java I believe for bufferreader
        if(bufferReader!= null) // I believe it means if the bufferreader is still going
            bufferReader.close(); // Close it
    }
    
    private  void Calculations(int index, double count, int length){
        probability[index] = (count/(length));  // Inserts at the appropriate index each probability, where formula is number of letters / total, which is what is done here
        arc_angles[index] = probability[index]*360; // This formula follows angle = probability*2pi, but converting to degrees you multiply by 180/pi, which cancels the pis and gives 360
        //System.out.println(probability[index]); Was used to test if it's working properly
    }
    
    private  void arraySorter(){ // An array sorter I built, explained why at the end
        int temp_num; // Holds temporary number value
        char temp_letter; // Holds temporary letter character
        boolean sorted = false; // a boolean representing if the array is sorted, starting at false
        while(sorted == false){ // While its false
            sorted = true; // Make it true to break the loop after this for loop, unless.. 
            for(int i = 0; i < histogram.length-1; i++){ // n letters - 1 comparisons. 
                if(histogram[25-i] < histogram[24-i]){ // If the last index(as in the last element in the array) is less then the previous one
                    temp_num = histogram[24-i]; // Assign the temp num to the previous one
                    temp_letter = alphabet[24-i]; // Assign the temp letter to the previous one
                    histogram[24-i] = histogram[25-i]; // swap the numbers
                    alphabet[24-i] = alphabet[25-i]; // swap the letters
                    histogram[25-i] = temp_num; // swap the numbers
                    alphabet[25-i] = temp_letter; // swap the letters
                    sorted = false; // set this back to false as we need to check again, and it will do this until its sorted from least to greatest
                }
            }
        }
        //System.out.println(Arrays.toString(histogram)); Used to test..
        //System.out.println(Arrays.toString(alphabet));
    }
    
   private  void colorGenerator(){
       // Color Generator
       int R = (int) (Math.random() * 256); // This is a random color generator I made in exercise 1 that I brought here
       int G = (int) (Math.random() * 256); // It will make random colors on each call
       int B = (int) (Math.random() * 256);
       colorGenerator = new Color(R,G,B); // Random colors for the pie chart, assigned to the Color variable colorGenerator
       //
   }
    
    public  void drawPieChart(){ // Draw pie chart method
        arraySorter(); // Call to my array sort
        Arrays.sort(probability); // Javas sorter for array, I explain why I used my own and javas at the end of the file
        Arrays.sort(arc_angles);// Sorts angles
        
        // Creating panel and frame 
        JFrame frame = new JFrame("Histogram Program - John Arena - Homework 5 - CSC 221");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //
        JButton button_input = new JButton("Enter number of letters"); // Creates buttons
        JButton button_draw = new JButton("Draw Pie Chart!");
        JTextField textField = new JTextField(); // Textfields to enter amount of letters
        Font textFont = new Font("SansSerif", Font.BOLD, 30);
        Font buttonFont = new Font("SansSerif", Font.BOLD, 13);
        textField.setFont(textFont); // Sets text field font
        button_input.setFont(buttonFont); // Sets buttons' fonts
        button_draw.setFont(buttonFont);
        
        //Creates another seperate panel for the south part of the main panel, this lets us position certain stuff specifically in the south part of the main panel 
        JPanel southPanel = new JPanel(new BorderLayout(50,50)); // Makes a border layout with gaps between the buttons etc
        panel.add(southPanel, BorderLayout.SOUTH); // Adds the south panel to the main panel
        southPanel.setPreferredSize(new Dimension(700,50)); // size of the south panel
        southPanel.add(button_input, BorderLayout.WEST); // Adds the buttons and textfields to the appropriate positions...
        southPanel.add(textField, BorderLayout.CENTER);
        southPanel.add(button_draw, BorderLayout.EAST);
        // 
        
        // Sets frame properties last and adds the panel to the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.add(panel);
        frame.setSize(1000,720);
        frame.setLocationRelativeTo(null); // Centers to the screen
        frame.setVisible(true);
        frame.setResizable(false); // Doesn't allow resizing 
        JOptionPane.showMessageDialog(null, "Welcome to the program! Please enter the amount of letters you wish to see that appear the most in the file","Welcome!",JOptionPane.INFORMATION_MESSAGE);
        //
        
        button_input.addActionListener(new ActionListener() // An action listener for when you press the button intended for submitting the amount of letters 
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    //if(n > 0 && n < 26) I tried to set a method for catching when you enter a negative number or number greater te amount of letters in alphabet, but it interfered with the catch statement
                    n = Integer.parseInt(textField.getText());
                    colorGenerator();
                    //else
                    //    JOptionPane.showMessageDialog(null,"The alphabet only has 26 letters. Please try again and enter an integer from 1 to 26!","Number out of range!",JOptionPane.ERROR_MESSAGE);
                }
                catch (NumberFormatException nfe) { // I kept the catch statement as I figured people would most likely know the alphabet is 26 letters, while you may enter number spelled out instead, so I added a catch
                    JOptionPane.showMessageDialog(null,"This is not an integer! Please try again!","Improper Input!",JOptionPane.WARNING_MESSAGE); // Displays warning message
                }
            }
        });
        button_draw.addActionListener(new ActionListener() // Action listener for when the draw button is pressed
        {
            public void actionPerformed(ActionEvent e)
            {
                JPanel chart = new JPanel(){ // We create a new panel called chart
                    public void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        double start_angle = 0; // Starting angle at 0, 
                        double total = 0; // This is for the degrees of the arcs for each letter added together
                        int count = 0; // Count variable, used for the pie chart key, specifically the all other letters key part of the chart
                        double other_letters = 0;
                        //double ang_adjust = 0;
                        
                        //This is to increase the thickness of the border for the key for the pie chart
                        Graphics2D g2 = (Graphics2D) g;
                        int thickness = 2;
                        Stroke stroke = g2.getStroke();
                        g2.setStroke(new java.awt.BasicStroke(thickness));
                        g2.drawRect(700,3,300,635);
                        g2.setStroke(stroke);
                        //
                        
                        for(int i = 0; i < n; i++) { // Loops through the amount user specified in the text field
                            colorGenerator();
                            g.setColor(colorGenerator); // Gets a color from color generator
                            g.fillArc(100, 75, 500, 500, (int) start_angle ,(int) Math.round(arc_angles[25-i])); //Arc with x,y,x_length,y_length,starting angle,arc angle. We start with start angle at 0, which is 3 o clock, working counter-clckwise
                            start_angle = (int) (start_angle + Math.round(arc_angles[25-i])); //the next starting angle, adding the last start angle to the new arc angle, giving us the new starting angle point
                            total += Math.round(arc_angles[25-i]); // This is to add each arc angle, finding the total angle size of all the arcs from the amount of letters specified
                            if(probability[25-i] != 0){ // This is to display the non-zero probabilities in the key
                                g.setColor(Color.black); // sets the text to black
                                g.setFont(new Font("default", Font.BOLD, 14)); // sets the font type, makes it bold, and the size. I couldn't change the font type for some reason
                                g.drawString(Character.toString(alphabet[25 - i]) + ",", 825, 30 + (24 * i)); // Displays the letter for each loop moving down the y axis each loop for the next letter. 
                                g.drawString(Float.toString((float) probability[25 - i]), 842, 30 + (24 * i)); // I originally had this and the above statement combined, but since each letter are different sizes, the probabilities were not lining up and made it look sloppy. So i made 2 lines instead
                                g.setColor(colorGenerator); // Calls colorGenerator again for..
                                g.fillRect(775, 15 + (24 * i), 30, 20); // Creates colors next to the corresponding letter and probability, matching it with the pie chart colors
                                other_letters +=probability[25-i]; // Will be used to calculate probability of other letters
                            }
                            count++; // Counts up
                            // The code blocked out below was a failed attempt to implement a way to print probabilities next to each part of the pie chart, using triangle method
                            //from previous exercises to get the point
                            
                            /*ang_adjust += (arc_angles[25-i]/2);
                            g.drawString("Test",(int)(100+(700*Math.cos(Math.toRadians(360-(ang_adjust/2))))),(int)(75+(700*Math.sin(Math.toRadians(360-(ang_adjust/2))))));
                            System.out.println((100+(700*Math.cos(Math.toRadians(135-(ang_adjust/2))))) + "____" + (75+(700*Math.sin(Math.toRadians(135-(ang_adjust/2))))));
                            System.out.println(Math.cos(Math.toRadians(45-30)));
                            g.setColor(Color.black);
                            g.drawString("test",50,50);
                            g.drawString("test2",50,75);
                            ang_adjust += (arc_angles[25-i]/2); */
                            
                        }
                        g.setColor(Color.blue); // Sets the color of the all other letters part of the pie chart
                        if(total != 360){ // Basically this says if the user didn't input 26 as in all the letters of the alphabet
                            if(total < 360 ) { // If the amount of angles is not 360, as in we didn't input a value meaning all the letters of the alphabet
                            g.fillArc(100,75,500,500,(int)start_angle,(int)(360-total));}  // We fill in the remaining chunk of the pie chart for the "all other letters" portion
                            g.setColor(Color.blue); // set it to blue
                            g.fillRect(775,(24*(count))+15,30,20); //We are adding a all other letters part of the key if there is less then 26 letters 
                            g.setColor(Color.black); // set font color
                            g.setFont(new Font("default", Font.BOLD, 14)); // font properties
                            g.drawString("All other letters, " + (float)(1-other_letters) , 825, 30 + (24 * (count))); // string drawn on key for all other letters, including probabilitiy of other letters by doing 1-other_letters
                        }
                        
                    }
                };
                // This was an attempt to add the key as a seperate panel, but doing so never let the pie chart and key have corresponding colors
                //
                /*JPanel key = new JPanel() { 
                    public void paintComponent(Graphics g){
                        super.paintComponent(g);
                        int count = -1;
                        for(int i = 0; i < n; i++) {
                            g.setColor(Color.black);
                            g.setFont(new Font("default", Font.BOLD, 14));
                            g.drawString(Double.toString(probability[25-i]), 150, 30 + (24*i));
                            //g.drawString(Integer.toString((int)arc_angles[24]),150,50);
                            g.setColor(colorGenerator2);
                            g.fillRect(100, 15+(24*i), 30, 20);
                            count++;
                        }
                        if (n != 26) {
                        g.setColor(Color.blue);
                        g.fillRect(100, (24 * (count + 1)) + 15, 30, 20);
                        g.setColor(Color.black);
                        g.setFont(new Font("default", Font.BOLD, 14));
                        g.drawString("All other letters", 150, 30 + (24*(count + 1)));
                        }
                    }
                }; */
                //
                
                //key.setPreferredSize(new Dimension(300,50))
                //panel.add(key, BorderLayout.EAST);
                //key.setBorder(BorderFactory.createLoweredBevelBorder());
                //key.setBorder(BorderFactory.createRaisedBevelBorder());
                panel.add(chart, BorderLayout.CENTER); // Adds the chart panel to the main panel as the center portion
                chart.setBorder(BorderFactory.createLoweredBevelBorder()); // This creates just borders around the chart panel
                chart.setBorder(BorderFactory.createRaisedBevelBorder()); // same here..
                frame.validate(); // When you press Draw, it updates the panel to display the chart
            }
        });
    }
}
/*This was a fun assignment. Originally I had a loop for while we have an input, just add it to a string. This worked for small amount of words, but when I tried to the one provided
to use for testing, it seems my laptop couldn't handle it. I then went to try this on my desktop which is much more powerful (I put it together myself and have pretty good hardware, 
should be able to run it no problem). Well I was wrong, it was going faster then my laptop, much faster, but still was taking a while. I thought to myself this must be inefficient, 
there has to be another more efficient way. I tested various methods on my desktop and found using a bufferedreader with a stringbuilder to be the fastest. Infact, on my desktop it was within
3-7 seconds. So I implemented on my laptop, and it seems my laptop is just too slow to handle it. I left it running and total run time was 29 minutes, and it still didn't finish. I was speechless.
I decided to continue testing my program with a smaller file I made. 

Now why did I make my own array sorter? Well originally I used the sort method for the histogram, but I couldn't figure out a way to get the corresponding letters after its sorted
So while I have my for loop looping through letters a-z in my readFile function, I filled up a character array called alphabet along with the histogram array, that way each one corresponds
to each other. Now in my arraySorter function, I basically sort the histogram array comparing the values, and then move the letters everytime i move a number in histogram. For example
histogram = {4, 6, 3}
alphabet = {a , b, c}
After sorting (least to greatest by the way..)
histgoram = {3, 4 ,6}
alphabet = {c, a, b}
This is basically how it works, then each index of the array corresponds to the correct number of letters and which letter specifically, and then I can use this for the key for the pie chart

Now in the drawPiechart method, I have my first method call to my arraySort function, but then used java's sort method for the two other arrays, just to show they both function essentially the same
way

In there I create a frame with a panel. Now I chose to have my buttons with my text field for input at the bottom. So I added another panel to the south panel of the original, so then I can use a 
west, center, and east of the south panel. Then I add button action listeners. I set n = to the number passed in. I have a catch statement if the user enters a non-number into the field, informing them
what they did incorrectly in using the program.

For the pie chart and key, most of it was explained with comments in the code, but the main idea is lets say the user inputs 5, so the first 5 letters with the biggest probability are displayed
in the pie chart with the key, and the remaining space is filled and labeled as all other letters. If there is 26 letters, all other letters section and key label do not get drawn onto the panel

I originally tried making pie chart and the key their own seperate panels, west and east, but this wouldn't let me assign corresponding colors for the key and the sections of the pie chart, as each
panel made 2 different calls each time to the color function, giving each panel 2 different colors each time. I left it to take a look if you're curious
*/