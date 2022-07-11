import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
public class csvComparator {
    public static void main(String[] args) {
        System.out.println("File is running");
        if(args.length == 0){
            System.out.println("You have not inputted the required files.");
        }
        else{
            try{
                File firstfile = new File(args[0]);
                File secondfile = new File(args[1]);
                FileWriter outfile = new FileWriter(args[2]);
                System.out.println(firstfile.getName());
                System.out.println(secondfile.getName());
                Scanner firstscan = new Scanner(firstfile);
                Scanner secondscan = new Scanner(secondfile);
                while(firstscan.hasNext() && secondscan.hasNext()){ // this is fine as the test files are sorted.
                    String[] x = firstscan.next().replace("\n","").split(",");
                    String[] y = secondscan.next().replace("\n","").split(",");
                    System.out.println(Arrays.toString(y));
                    int csvlength = x.length;
                    for(int i = 0; i < csvlength; i++){ // this is for expanding features if targetted comparison is required.
                        if(!(x[i].equals(y[i]))){
                            System.out.println("x's array" + x[i]);
                            System.out.println("y's array: " + y[i]);
                            String output = String.join(",",x) + "\n" + String.join(",",y) + "\n";
                            outfile.write(output);
                            break;
                        }
                    }
                }
                //clean up
                outfile.close();
                firstscan.close();
                secondscan.close();
                }
                catch (FileNotFoundException e) {
                    System.out.println("An error occurred. Your inputs are broken.");
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println("An error occurred. You do not have an existing file");
                    e.printStackTrace();
                }
                }
            }
        }

    

