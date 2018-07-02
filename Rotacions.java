import java.io.*;
import java.util.Scanner;
class Rotacions
{
    public static void main(String [] args)
    {
        String  s1="waterbottle";
        String  s2="erbottlewat";
        Scanner sc=new Scanner(System.in);
        System.out.println("Escriba la cadena s1");
        s1=sc.next();
        System.out.println("Escriba la cadena s2");
        s2=sc.next();
        String s1s1=s1 + s1;
        if (s1s1.contains(s2))
            System.out.println("s1 es rotación de s2"); //true
        else
            System.out.println("s1 no es rotación de s2"); //true
    }
}
