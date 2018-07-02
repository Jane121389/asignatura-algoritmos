/*programa que recibe como parametro de entrada los enteros k y t, seguido por una colección de cadenas de ADN y da como resultado una colección de cadenas BestMotifs resultantes de correr GreedyMotifSearch ( Dna , k , t) . */
import java.io.*;
import java.util.Scanner;
class GreedyMotif2
{
    public static void main(String [] args)
    {
        int     motif[][];       //coloca los vecinos de la primera cadena
        int     motif_nuevo[][]; //coloca los vecinos de la primera cadena
        String  cadenas_adn[];   //muestra las cadenas de adn introducidas por el usuario
        int     construye_cadena[][];
        int     kmer       =0; //k-mer
        int     m_count[][]=new int[4][kmer];
        int     indice[];             //indíce del numero de letras de las cadenas k-mer
        int     ikmer;                //indíce de letras de las cadenas k-mer
        char    letra           =' '; //Contendra una letra  de un ácido nucléico de la cadena de ADN
        int     valor_letra     =0;   //valores que asignamos a los ácidos nucléicos (A=1,T=2,C=3,G=4);
        int     numero_cadenas  =0;   //número de cadenas
        int     longitud_cadenas=0;   //loongitud de las cadenas
        int     fila            =0;
        int     ii              =0;
        int     score_anterior  =10000;
        int     score_nuevo     =0;
        int     aux_arreglo[]   =new int[kmer];
        Scanner sc              =new Scanner(System.in);            //instancia para entrada de datos por el teclado
        System.out.println("Escriba el numero de cadenas de ADN:"); //Salida en la pantalla
        numero_cadenas=sc.nextInt();                                //Recepción de la cadena de ADN
        System.out.println("Escriba el k-mer:");                    //Salida en la pantalla
        kmer       =sc.nextInt();                                   //Recepción del k-mer
        cadenas_adn=new String[numero_cadenas];                     //define numero_cadenas elementos en el arreglo cadenas_adn
        System.out.println("Escriba las cadenas de ADN");
        for (int nc=0; nc < numero_cadenas; nc++)
            cadenas_adn[nc]=sc.next();                  //obtiene del teclado las cadenas de ADN
        longitud_cadenas=cadenas_adn[0].length();       //Obtiene la longitud de las cadenas de ADN
        motif           =new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo motif
        construye_cadena=new int[numero_cadenas][kmer]; //define la cantidad de elementos del arreglo motif
        motif_nuevo     =new int[kmer][kmer];           //define la cantidad de elementos del arreglo moti
        indice          =new int[kmer];                 //define kmer elementos en el arreglo indice
        for (ii=0; ii < kmer; ii++)                     //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (int nc=0; nc < numero_cadenas; nc++)
            for (int tc=0; tc < kmer; tc++)
            {
                letra=cadenas_adn[nc].charAt(tc);   //letra de la cadena de ADN leída
                switch (letra)
                {
                    case 'A':          //Si la letra es A (adenina)
                        valor_letra=0; //valor que se le asigna a la adenina (A)
                        break;
                    case 'C':          //Si la letra es C (citosina)
                        valor_letra=1; //valor que se le asigna a la citosina (C)
                        break;
                    case 'G':          //Si la letra es G (guanina)
                        valor_letra=2; //valor que se le asigna a la guanina (G)
                        break;
                    case 'T':          //Si la letra es T (timina)
                        valor_letra=3; //valor que se le asigna a la timina (T)
                        break;
                    default:
                        valor_letra=-1;
                        break;
                }
                motif[nc][tc]=valor_letra;
            }
        for (int tc=0; tc < longitud_cadenas; tc++)
        {
            letra=cadenas_adn[0].charAt(tc);    //letra de la cadena de ADN leída
            switch (letra)
            {
                case 'A':          //Si la letra es A (adenina)
                    valor_letra=0; //valor que se le asigna a la adenina (A)
                    break;
                case 'C':          //Si la letra es C (citosina)
                    valor_letra=1; //valor que se le asigna a la citosina (C)
                    break;
                case 'G':          //Si la letra es G (guanina)
                    valor_letra=2; //valor que se le asigna a la guanina (G)
                    break;
                case 'T':          //Si la letra es T (timina)
                    valor_letra=3; //valor que se le asigna a la timina (T)
                    break;
                default:
                    valor_letra=-1;
                    break;
            }
            for (ikmer=0; ikmer < kmer; ikmer++)     //recorre los arreglos
            {
                if (indice[ikmer] >= 0)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1

                    construye_cadena[ikmer][indice[ikmer]]=valor_letra; //concatena las letras (ácidos nucleicos)
                indice[ikmer]=indice[ikmer] + 1;                        //aumenta en uno los indices de las letras de las cadenas
                if (indice[ikmer] >= kmer)                              //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                {
                    motif_nuevo=motif;
                    for (ii=0; ii < kmer; ii++)
                        motif_nuevo[0][ii]=construye_cadena[ikmer][ii];
                    for (int n_c=1; n_c < numero_cadenas; n_c++)
                    {
                        m_count    =count(motif_nuevo, kmer, n_c);
                        aux_arreglo=probables(cadenas_adn[n_c], kmer, m_count, n_c);
                        for (ii=0; ii < kmer; ii++)
                            motif_nuevo[n_c][ii]=aux_arreglo[ii];

                    }
                    m_count    =count(motif_nuevo, kmer, numero_cadenas);
                    score_nuevo=score(m_count, kmer, numero_cadenas);
                    if (score_nuevo < score_anterior)
                    {
                        motif         =motif_nuevo;
                        score_anterior=score_nuevo;
                        System.out.println("final");
                        for (int i=0; i < numero_cadenas; i++)
                        {
                            for (int j=0; j < kmer; j++)
                            {
                                if (motif[i][j] == 0)
                                    letra='A';
                                else if (motif[i][j] == 1)
                                    letra='C';
                                else if (motif[i][j] == 2)
                                    letra='G';
                                else if (motif[i][j] == 3)
                                    letra='T';
                                System.out.print(letra);
                            }
                            System.out.println("");
                        }
                    }
                    indice[ikmer]=0;    //inicializa el índice de la cadena
                }
            }
        }
    }

    static int [][] count(int [][] motif, int kmer, int numero_cadenas)
    {
        int m_count[][]=new int[4][kmer];
        for (int j=0; j < kmer; j++)
            for (int i=0; i < 4; i++)
                m_count[i][j]=0;
        for (int j=0; j < kmer; j++)
            for (int i=0; i < numero_cadenas; i++)
                m_count[motif[i][j]][j]=m_count[motif[i][j]][j] + 1;
        return m_count;
    }

    static int score(int [][] m_count, int kmer, int total_cadenas)
    {
        int score_t=0;
        int score_c=0;
        for (int j=0; j < kmer; j++)
        {
            score_c=0;
            for (int i=0; i < 4; i++)
                if (m_count[i][j] > score_c)
                    score_c=m_count[i][j];
            score_t=score_t + (total_cadenas - score_c);
        }
        return score_t;
    }

    static int [] probables(String cadena, int kmer, int [][] probabilidades, int contador_cadenas)
    {
        int   longitud_cadenas=cadena.length();
        char  letra           =' ';           //letra de la cadena
        int   valor_letra     =0;             //valor de la letra
        int   ikmer           =0;             //indice de las letras del kmer
        int   indice[]        =new int[kmer]; //define kmer elementos en el arreglo indice
        int   construye_cadena[][];           //Construccion de las cadenas kmer
        float probabilidad_mayor=0;
        float probabilidad_total=0;
        float probabilidad[]    =new float[kmer]; //define kmer elementos en el arreglo probabilidad
        construye_cadena=new int[kmer][kmer];     //define kmer elementos en el arreglo construye_cadena
        int   ii                =0;
        int   num_c             =0;
        float probabilidad_letra=0;
        int   cadena_probable[] =new int[kmer];
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de las probabilidad con uno
            probabilidad[ii]=1;
        for (ii=0; ii < kmer; ii++) //inicializa el arreglo de los índices de número de letras de las cadenas k-mer del 0 al -kmer
            indice[ii]=0 - ii;
        for (int tc=0; tc < longitud_cadenas; tc++)
        {
            letra=cadena.charAt(tc);    //letra de la cadena de ADN leída
            switch (letra)
            {
                case 'A':          //Si la letra es A (adenina)
                    valor_letra=0; //valor que se le asigna a la adenina (A)
                    break;
                case 'C':          //Si la letra es C (citosina)
                    valor_letra=1; //valor que se le asigna a la citosina (C)
                    break;
                case 'G':          //Si la letra es G (guanina)
                    valor_letra=2; //valor que se le asigna a la guanina (G)
                    break;
                case 'T':          //Si la letra es T (timina)
                    valor_letra=3; //valor que se le asigna a la timina (T)
                    break;
                default:
                    valor_letra=-1;
                    break;
            }
            for (ikmer=0; ikmer < kmer; ikmer++)    //recorre los arreglos
            {
                if (indice[ikmer] >= 0 && valor_letra != -1)//si el índice de la cadena es menor al número k-mer y la letra tiene un valor diferente de -1
                {
                    construye_cadena[ikmer][indice[ikmer]]=valor_letra;
                    probabilidad_letra                    =(probabilidades[valor_letra][indice[ikmer]]); //Busca la probabilidad en la matriz de probabilidades
                    //System.out.print("contador_cadenas:"+contador_cadenas);
                    probabilidad[ikmer]=probabilidad[ikmer] * (probabilidad_letra / contador_cadenas);  //multiplica la probabilidad
                }
                indice[ikmer]=indice[ikmer] + 1;                //aumenta en uno los indices de las letras de las cadenas
                if (indice[ikmer] >= kmer && valor_letra != -1) //si el índice de la cadena es menor a cero y la letra tiene un valor diferente de -1
                {
                    if (num_c == 0)
                        for (ii=0; ii < kmer; ii++)
                            cadena_probable[ii]=construye_cadena[ikmer][ii];
                    num_c++;
                    indice[ikmer]     =0;                   //inicializa el índice de la cadena
                    probabilidad_total=probabilidad[ikmer]; //coloca la probabilidad total de la cadena kmer
                    if (probabilidad_total > probabilidad_mayor)
                    {
                        probabilidad_mayor=probabilidad_total;
                        for (ii=0; ii < kmer; ii++)
                            cadena_probable[ii]=construye_cadena[ikmer][ii];
                    }
                    probabilidad[ikmer]=1;      //se inicializa en uno el elemento del arreglo probabilidad
                }
            }
        }
        return cadena_probable;
    }
}
