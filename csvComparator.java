import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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
                // portion for unique combination.
                if(args.length > 3){
                    String[] criteria_list = new String[args.length - 3];
                    String[] first_line_criteria = firstscan.nextLine().split(",");
                    String[] second_line_criteria = secondscan.nextLine().split(",");
                    // System.out.println("x's array" + Arrays.toString(first_line_criteria));
                    Map<String, Integer> first_map = new HashMap<>();
                    Map<String, Integer> second_map = new HashMap<>();
                    //make a hashmap out of the arrays, keeping the index in check. Might need stripping
                    for(int i = 0; i < first_line_criteria.length; i++){
                        first_map.put(first_line_criteria[i], i);
                    }
                    for(int i = 0; i < second_line_criteria.length; i++){
                        second_map.put(first_line_criteria[i], i);
                    }
                    System.out.println("hashmap looks like this: " + Collections.singletonList(first_map));
                    //store the args after pos2 and check if they are the valid comparators.
                    for(int i = 3; i < args.length; i++){
                            String criteria = args[i];
                            criteria_list[i - 3] = criteria;
                    }
                    // check if the criterias match up.
                    Arrays.sort(first_line_criteria);
                    Arrays.sort(second_line_criteria);
                    Arrays.sort(criteria_list);
                    try {
                        // check if criteria list length is shorter than first line, then make an exception
                        if(first_line_criteria.length > criteria_list.length || second_line_criteria.length > criteria_list.length){
                            firstscan.close();
                            secondscan.close();
                            outfile.close();
                            throw new Exception();
                        }
                        String[] min_array;
                        if(first_line_criteria.length < second_line_criteria.length){
                            min_array = first_line_criteria;
                        }
                        else{
                            min_array = second_line_criteria;
                        }
                        //loops thru the small csv and the criteria list, if there is a differnece, throw an error
                        for(int i = 0; i < criteria_list.length; i++){
                            if(!(min_array[i].equals(criteria_list[i]))){
                                firstscan.close();
                                secondscan.close();
                                outfile.close();
                                throw new Exception();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Criteria does not match the headers in csv files.");
                        
                    }
                }
                while(firstscan.hasNext() && secondscan.hasNext()){ // this is fine as the test files are sorted.
                    String[] x = firstscan.nextLine().replace("\n","").split(",");
                    String[] y = secondscan.nextLine().replace("\n","").split(","); // used to get the individual values
                    // System.out.println(Arrays.toString(y));
                    int csvlength = x.length;
                    for(int i = 0; i < csvlength; i++){ // this is for expanding features if targetted comparison is required.
                        if(!(x[i].equals(y[i]))){
                            // System.out.println("x's array" + x[i]);
                            // System.out.println("y's array: " + y[i]);
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


