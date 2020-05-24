public class maths{
    public static void main(String[] args ){

        int i = 2;
        i = i*i + i;
        for(int j = 0; j > 50 ; j++){
            if(i >= 30 && j > 30){
                break;
            }
            i *= j*2;
        }

    }
}


