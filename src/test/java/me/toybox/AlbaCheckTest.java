package me.toybox;

import java.io.*;

/**
 * Created by sunkyung on 2018. 4. 18..
 */
public class AlbaCheckTest {

    public void check() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter("data/result.tsv"));

        int countA = 1;
        int countB = 2;
        int countC = 3;

        String compareDate = "";

        try {
            File file = new File("data/alba_dl.tsv");
            BufferedReader rd = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = rd.readLine()) != null) {

                String[] temp = line.split("\t");
                String name = temp[1];
                String state = temp[2];
                String date = getDate(temp[3]);

                if(compareDate.equals(date)){
                    if ("design1(성상현)".equals(name) && "1".equals(state)) {
                        countA++;
                    }
                    if ("design2(송준영)".equals(name) && "1".equals(state)) {
                        countB++;
                    }
                    if ("design3(장아람)".equals(name) && "1".equals(state)) {
                        countC++;
                    }
                }
                else {
                    compareDate = date;
                    out.write(date + "\t" +countA+"\t" +countB+"\t" +countC +"\n");
                    countA = 0;
                    countB = 0;
                    countC = 0;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.close();


    }
    public static String getDate(String date){
        String result = date.split(" ")[0];
        return result;
    }
}
