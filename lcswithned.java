/*
 * 
 * @author Anand Kumar Dharmaraj
 * 800867560
 * ITCS 6114 - Programming Project 1
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class LCSwithNED {
    

    // computeED calculates the edit distance, it takes two strings as input  
    // computeED initializes two single dimensional arrays and assigns values to one of them
    public static int computeED(String a, String b)
    {
       int d=0;
       
       // computeED uses two single dimensional arrays x and y to compute the edit distance
       int[] x=new int[b.length()+1];
       int[] y=new int[b.length()+1];
       
       // the first iteration has a null string compared to string b
       // the result of this comparison is initially stored in x 
       for(int i=0;i<b.length()+1;i++){
           x[i]=i;
       }
       
       // after initialization, the array y is calculated iteratively using the computefn function
       d=computefn(x,y,a,b);
       
       // d is the edit distance to be returned
       return d; 
    }
    // computefn takes two single dimensional arrays and calculates
    // the values of the second array iteratively based on the two input strings
    public static int computefn(int[] x, int[] y, String a, String b)
    {
        for(int i=1;i<=a.length();i++)
        {
            // initialize the first value of y with 1 more than the corresponding x array value
            // as the string grows by one character, when the string is compared with null string, it will give a edit distance
            // value of one more than the previous value
            y[0]=x[0]+1;
            
            // calculate all other values of the y array based on the three neighbours
            for(int j=1;j<=b.length();j++)
            {
                // if the two characters considered are equal, the value in array element is equal to the diagonal neighbour
                if(a.charAt(i-1)==b.charAt(j-1))
                {
                    y[j]=x[j-1];
                }
                // if they are not equal, we compare the other two neighbours, assign a value equal
                // to one more than the lowest neighbour
                else
                {
                    if(x[j]>y[j-1])
                    {
                        // assign value one more than the left neighbour
                        y[j]=y[j-1]+1;
                    }
                    // else assign value one more than top neighbour
                    else
                        y[j]=x[j]+1;
                }
            }
            
            // assign y array values to x and set y array values to 0 for the next iteration
            for(int k=0;k<=b.length();k++)
            {
                x[k]=y[k];
                y[k]=0;
            }
        }
            
            // return the last value in the final x array (that is same as the final y array formed)
            int blen=b.length();
            return x[blen];
    }
    
    // function to calculate normalized edit distance
    public static float calNED(int ed, String s1, String s2)
    {        
        // Compute the Normalized Edit Distance
        float x1 = s1.length()+s2.length()-ed;
        float x2 = s1.length()+s2.length();
        float ned = x1/x2;
        return ned;
    }
    // formtable takes two input strings and initializes a two dimensional array for use
    // it also assigns values to the first row of the array
    public static int[][] formtable(String a, String b)
    {
        // initialize a MxN two dimensional array/matrix to store the edit distance table
        int[][] x=new int[a.length()+1][b.length()+1];
        
        // the first iteration has a null string compared to string b
        // the result of this comparison is initially stored in x 
        for(int i=0;i<b.length()+1;i++){
            x[0][i]=i;
        }
        
        //calculate the remainder of the table using the formtablefn function
        int[][] y=formtablefn(x,a,b);
        
        // print the table if necessary
        /*
        System.out.println("The edit distance table is as follows:");
        for(int k=0;k<a.length()+1;k++)
        {
            for(int l=0;l<b.length()+1;l++)
            {
                System.out.print(y[k][l]);
            }
            System.out.println();
        }
        */
        
        // return the edit distance table
        return y;
    }
    
    // formtablefn function forms the table after initialization is done by formtable function
    public static int[][] formtablefn(int[][] x,String a, String b)
    {
        // fill all rows from 1 to length of string a (left aligned string)
        for(int i=1;i<=a.length();i++)
        {
            // first value of the row is equal to one more than the previous value
            // string length one more than previous string, when compared to nul string, gives edit distance one more than previous
            x[i][0]=x[i-1][0]+1;
            
            // fill all the columns of the row
            for(int j=1;j<=b.length();j++)
            {
                // if the characters are equal
                if(a.charAt(i-1)==b.charAt(j-1))
                {
                    // move to the top left diagonal neighbour
                    x[i][j]=x[i-1][j-1];
                }
                // if characters are not equal
                else
                {
                    if(x[i-1][j]>x[i][j-1])
                    {
                        // assign value one more than the left neighbour
                        x[i][j]=x[i][j-1]+1;
                    }
                    else
                    {
                        // else assign value one more than top neighbour
                        x[i][j]=x[i-1][j]+1;
                    }
                }
            } 
        }
        // return the formed table
        return x;
    }
    
    // constructlcs function takes the edit distance table and the two strings to compute the lcs
    public static String constructlcs(int[][] x,String a, String b)
    {
        // initialize an emty string
        String s="";
        Stack st=new Stack();
        
        // length of strings in two variables
        int alen=a.length();
        int blen=b.length();
    
        int i=alen,j=blen;
        
        // until reaching the top or left of the table
        while(i>0 && j>0)
        {   
            // if the two characters are equal
            if(a.charAt(i-1)==b.charAt(j-1))
            {
                
                
                // add the character thats equal to the temporary string
                // or push it into a stack as instructed in the project description
                
                //s=a.charAt(i-1)+s;
                
                st.push(a.charAt(i-1));
                // push a space into the stack to fulfill the output requirements
                st.push(" ");

                
                //System.out.println(s);
                
                // move to the diagonal top left neighbour
                i--;
                j--;
            }
            
            // if characters are not equal
            else
            {
                if(x[i][j-1]<=x[i-1][j])
                {
                    // move to the left neighbour
                    j--;
                }
                else
                {
                    // move to the top neighbour
                    i--;
                }
            }
        }
        
        // pop the first element, it will be a space as spaces are pushed after each character push
        st.pop();
        // get the values from the stack by popping the elements and appending to a string
        int stacksize=st.size();
        
        for(int k=0;k<stacksize;k++)
        {
            s=s+st.pop();
        }
        // return string s
        return s;
    }
    
    // fmrow calculates the forward middle row, it takes two strings as input  
    public static int[] fmrow(String a, String b, int m)
    {
       // fmrow uses two single dimensional arrays x and y to compute the forward middle row
       int[] x=new int[b.length()+1];
       int[] y=new int[b.length()+1];
       
       // the first iteration has a null string compared to string b
       // the result of this comparison is initially stored in x 
       for(int i=0;i<b.length()+1;i++){
           x[i]=i;
       }
       
       // after initialization, the array y is calculated iteratively using the iterate function
       int[] f=iterate(x,y,a,b,m);
       
       // d is the forward middle row to be returned
       return f; 
    }
    // iterate uses the initialized arrays of fmrow and calculates the array values iteratively m times based on the given strings
    public static int[] iterate(int[] x, int[] y, String a, String b, int m)
    {
        for(int i=1;i<=m;i++)
        {
            // initialize the first value of y with 1 more than the corresponding x array value
            // as the string grows by one character, when the string is compared with null string, it will give a edit distance
            // value of one more than the previous value
            y[0]=x[0]+1;
            
            // calculate all other values of the y array based on the three neighbours
            for(int j=1;j<=b.length();j++)
            {
                // if the two characters considered are equal, the value in array element is equal to the diagonal neighbour
                if(a.charAt(i-1)==b.charAt(j-1))
                {
                    y[j]=x[j-1];
                }
                // if they are not equal, we compare the other two neighbours, assign a value equal
                // to one more than the lowest neighbour
                else
                {
                    if(x[j]>y[j-1])
                    {
                        y[j]=y[j-1]+1;
                    }
                    else
                        y[j]=x[j]+1;
                }
            }
            // assign y array values to x and set y array values to 0 for the next iteration
            for(int k=0;k<=b.length();k++)
            {
                x[k]=y[k];
                y[k]=0;
            }
        }
        
            // return the final x array (that is same as the final y array formed)
            return x;
    }

    // rmrow calculates the reverse middle row, it takes two strings as input  
    public static int[] rmrow(String a, String b, int n)
    {
        
        // rmrow uses two single dimensional arrays x and y to compute the reverse middle row
        int[] x=new int[b.length()+1];
        int[] y=new int[b.length()+1];
        
        // the first iteration has a null string compared to string b
        // the result of this comparison is initially stored in x 
        for(int i=b.length();i>=0;i--){
            x[i]=b.length()-i;
        }
        
        // after initialization, the array y is calculated iteratively using the reiterate function
        int[] r=reiterate(x,y,a,b,n);
        // r is the reverse middle row to be returned
        return r; 
    }
    // reiterate takes the initialized arrays and calculates the array values iteratively for n times based on the given strings
    public static int[] reiterate(int[] x,int[] y,String a, String b, int n)
    {
        for(int i=a.length()-1;i>=n+1;i--)
        {
            // initialize the first value of y with 1 more than the corresponding x array value
            // as the string grows by one character, when the string is compared with null string, it will give a edit distance
            // value of one more than the previous value
            y[b.length()]=x[b.length()]+1;
            
          
            // calculate all other values of the y array based on the three neighbours
            for(int j=b.length()-1;j>=0;j--)
            {
                // if the two characters considered are equal, assign value for array element equal to the bottom diagonal neighbour
                // the diagonal is the bottom right neighbour
                if(a.charAt(i)==b.charAt(j))
                {
                    y[j]=x[j+1];
                }
                // if they are not equal, we compare the other two neighbours, assign a value equal
                // to one more than the lowest neighbour
                else
                {
                    if(x[j]>y[j+1])
                    {
                        // assign value one more than the right neighbour
                        y[j]=y[j+1]+1;
                    }
                    // else assign value one more than bottom neighbour
                    else
                        y[j]=x[j]+1;
                }
            }
            
            // assign y array values to x and set y array values to 0 for the next iteration
            for(int k=0;k<=b.length();k++)
            {
                x[k]=y[k];
                y[k]=0;
            }
            }
            // return the final x array (that is same as the final y array formed)
            return x;
    }
    // lcs_recursive is a recursive top down approach to constructing the lcs
    // it also maintains a stack passed as an argument to it to keep track of the characters in the lcs
    public static void lcs_recursive(String a,String b,Stack lcsstack)
    {
        // initialize an emty string
        //String estr="",fstr="";
        
        // base case: if string a's length is 1, compare it with all symbols in b and return the matched character if there is any
        if(a.length()==1)
        {
            // matchfound variable keeps track if a match is found
            int matchfound=0;
            //compare a.charAt(0) with string b, if match return the matched character
            for(int k=0;k<b.length();k++)
            {
                if(a.charAt(0)==b.charAt(k))
                {
                    matchfound=1;
                }
            }
            // if match found
            if(matchfound==1)
            {
                // push the matched character into a stack
                lcsstack.push(a.charAt(0));
                // push a space into the stack to fulfill the output requirements
                lcsstack.push(" ");
                
                // or append the character to a string and return the string
                // estr=estr+a.charAt(0);
                //return estr;
            }
        }
        // base case: if string b's length is 1, compare it with all symbols in a and return the matched character if there is any
        else if(b.length()==1)
        {
            // matchfound variable keeps track if a match is found
            int matchfound=0;
            //compare b.charAt(0) with string a, if match return the matched character
            for(int k=0;k<a.length();k++)
            {
                if(b.charAt(0)==a.charAt(k))
                {
                    matchfound=1;
                }
            }
            // if match found
            if(matchfound==1)
            {
                // push the matched character into a stack
                lcsstack.push(b.charAt(0));
                // push a space into the stack to fulfill the output requirements
                lcsstack.push(" ");
                
                
                // or append the character to a string and return the string
                //estr=estr+b.charAt(0);
                //return estr;
                
                
            }
        }
        // recursive case: if both string lengths are not equal to 1, then perform recursively the following operations
        else
        {
            // m variable has the horizontal split index
            int m=a.length()/2;
            // n variable is the remaining portion of the first string
            int n= a.length()-m;
            
            
            // print m and n if necessary
            //System.out.println("m is :"+m);
            //System.out.println("n is :"+n);
            // d now will have the forward middle row
            int[] d = fmrow(a,b,m);
            // e now will have the reverse middle row
            int[] e = rmrow(a,b,n);
            // print fmrow and rmrow if necessary
            /*
            for(int k=0;k<d.length;k++)
            {
                System.out.print(d[k]);
            }
            System.out.println();
            for(int k=0;k<e.length;k++)
            {
                System.out.print(e[k]);
            }
            System.out.println();
            */
            
            // a max value is stored in min element in order to facilitate finding the minimum sum of cross elements
            // in  forward middle row and reverse middle row
            int min=Integer.MAX_VALUE;
            // index variable will store the index (array calibrated) at which the vertical split will happen
            int index=0;
            
            // finding the minimum sum and its index location
            for(int k=0;k<d.length;k++)
            {
                int temp=d[k]+e[k];
                if(temp<min)
                {
                    index=k;
                    min=temp;
                }        
            }
            
            // print index and min if necessary
            /*
            System.out.println("The index is "+index);
            System.out.println("The min is "+min);
            */
            
            // initialize empty strings to store recursive broken strings 
            String xfront = "",yfront="", xback="", yback="";
            
            // form xfront
            for(int k=0;k<m;k++)
            {
                xfront=xfront+a.charAt(k);
            }
            
            // form xback
            for(int k=m;k<a.length();k++)
            {
                xback=xback+a.charAt(k);
            }
            
            // form yfront
            for(int k=0;k<index;k++)
            {
                yfront=yfront+b.charAt(k);
            }
            
            // form yback
            for(int k=index;k<b.length();k++)
            {
                yback=yback+b.charAt(k);
            }
            
            // print xfront,xback,yfront,yback if necessary
            /*
            System.out.println("Xfront is "+xfront);
            System.out.println("Xback is "+xback);
            System.out.println("Yfront is "+yfront);
            System.out.println("Yback is "+yback);
            */
            
            // recursively call the lcs_recursive function to solve the lcs problem
            //estr=lcs_recursive(xfront,yfront,lcsstack)+lcs_recursive(xback,yback,lcsstack);
            lcs_recursive(xfront,yfront,lcsstack);lcs_recursive(xback,yback,lcsstack);      
        }        
        
        // return the final estr which contains the longest common subsequence
        //return fstr;
    }
    // main method gets the files and sends values to functions in order to get the desired output
    public static void main(String args[]){
        
        try
        {
            //Initialize two empty strings
            String s1="",s2="";
            

            // Get two input strings from the user if necessary
            /*
            System.out.println("Enter the two strings : ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            s1=br.readLine();
            s2=br.readLine();
            */
            // get the input file paths from user
            System.out.println("Enter the path to the two input files (Hit enter after entering each file path): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String file1=br.readLine();
            String file2=br.readLine();
            
            // obtain the strings from the input files
            // the path of the files should be modified as necessary
            s1 = new Scanner(new File(file1)).useDelimiter("\\Z").next();
            s2 = new Scanner(new File(file2)).useDelimiter("\\Z").next();
            
            // open a new file or overwrite the file with given name
            PrintWriter writer = new PrintWriter("lcsoutput.txt", "UTF-8");

            int ed = computeED(s1,s2);
            
            // print edit distance if necessary
            //System.out.println("The ED is "+ed);
            
            float ned = calNED(ed,s1,s2);
            
            // write the normalized edit distance in the output file
            writer.println(ned);
            
 
            
            // compute the lcs using entire edit distance table
            // compute the longest common subsequence using formtable function and constructlcs function
            int[][] edt=formtable(s1,s2);
            
            // compute the longest common subsequence using the constructlcs function
            String lcs=constructlcs(edt,s1,s2);
            
            // print the lcs if necessary
            //System.out.println("Final LCS is "+lcs);
            
            // write lcs computed to output file
            writer.println(lcs);
            
            
            // initialize a stack to store matching characters
            Stack lcsstack=new Stack();
            // compute the lcs using linear memory and lcs_recursive function
            // lcs_recursive computes the longest common subsequence
            lcs_recursive(s1,s2,lcsstack);
            
             // get the values from the stack by popping the elements and appending to a string
            int stacksize=lcsstack.size();
            String lcsrec="",revlcs="";
            // the values popped out will be in reverse order
            for(int k=0;k<stacksize;k++)
            {
                revlcs=revlcs+lcsstack.pop();
            }
            
            // reverse string is in revlcs, get the right string in lcsrec
            for(int k=0;k<revlcs.length();k++)
            {
                lcsrec=lcsrec+revlcs.charAt(revlcs.length()-k-1);
            }
            
            // print the lcs if necessary
            //System.out.println("Final LCS is "+lcsrec);
            
            // write recursively computed lcs to output file
            writer.println(lcsrec);
 
            // close the print writer
            writer.close();
            
        }
        // Exceptions are reported in the catch section
        catch(Exception e)
        {
            System.out.println("Exception: "+e);
        }
    }    
}
