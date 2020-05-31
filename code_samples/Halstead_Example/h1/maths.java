public class maths{
    boolean trout = true;

    public static void main(String[] args ){

        int i = 2;
        i = i*i + i;
        for(int j = 0; j > 50 ; j++){
            if(i >= 30 && j > 30){
                break;
            }
            i *= j*2;
        }


        String fish = (trout != false) ? "bream" : "flatty";


        i += 2;
    }
}


