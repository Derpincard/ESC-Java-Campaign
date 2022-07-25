import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class csvComparator {
    public static void main(String[] args) {
        String[] criteria_list = null;
        Map<String, Integer> first_map = new HashMap<String,Integer>();
        Map<String, Integer> second_map = new HashMap<String,Integer>();
        //----------------------------------------------------------//
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
                    criteria_list = new String[args.length - 3];
                    String[] first_line_criteria = firstscan.nextLine().replace("\n","").replaceAll("\"", "").split(",");
                    String[] second_line_criteria = secondscan.nextLine().replace("\n","").replaceAll("\"", "").split(",");
                    // System.out.println("x's array" + Arrays.toString(first_line_criteria));
                    //make a hashmap out of the arrays, keeping the index in check. Might need stripping
                    for(int i = 0; i < first_line_criteria.length; i++){
                        first_map.put(first_line_criteria[i], i);
                    }
                    for(int i = 0; i < second_line_criteria.length; i++){
                        second_map.put(first_line_criteria[i], i);
                    }
                    // System.out.println("hashmap looks like this: " + Collections.singletonList(first_map));
                    //store the args after pos2 and check if they are the valid comparators.
                    for(int i = 3; i < args.length; i++){
                            String criteria = args[i];
                            criteria_list[i - 3] = criteria;
                    }
                    // check if the criterias match up.
                    try {
                        // check if criteria list length is shorter than first line, then make an exception
                        if(first_line_criteria.length < criteria_list.length || second_line_criteria.length < criteria_list.length){
                            firstscan.close();
                            secondscan.close();
                            outfile.close();
                            throw new Exception("length is wrong");
                        }
                        //loops thru the small csv and the criteria list, if there is a differnece, throw an error
                        for(int i = 0; i < criteria_list.length; i++){
                            String thingy = criteria_list[i];
                            if(!(first_map.containsKey(criteria_list[i]) && first_map.containsKey(criteria_list[i]))){
                                System.out.println("hashmap looks like this: " + Collections.singletonList(first_map));
                                System.out.println(criteria_list[i]);
                                System.out.println(first_map.get(thingy));
                                System.out.println(first_map.keySet().toArray()[0].equals(thingy));
                                System.out.println(first_map.keySet().toArray()[0]);
                                firstscan.close();
                                secondscan.close();
                                outfile.close();
                                System.out.println("Equivalence is wrong");
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
                    if(criteria_list == null){ // check if there args < 3, then just do normal comparison
                        for(int i = 0; i < csvlength; i++){
                            if(!(x[i].equals(y[i]))){
                                // System.out.println("x's array" + x[i]);
                                // System.out.println("y's array: " + y[i]);
                                String output = String.join(",",x) + "\n" + String.join(",",y) + "\n";
                                outfile.write(output);
                                break;
                            }
                        }
                    }
                    else{
                        for(int i = 0; i < criteria_list.length; i++){
                            // call like this: hashmap[string] to get the position of the element in the array
                            Integer c1 = first_map.get(criteria_list[i]); 
                            Integer c2 = second_map.get(criteria_list[i]);
                            System.out.println(c2);
                            if(!(x[c1].equals(y[c2]))){
                                System.out.println("Difference spotted at line:" + i);
                                String output = String.join(",",x) + "\n" + String.join(",",y) + "\n";
                                outfile.write(output);
                                break;
                            }
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


