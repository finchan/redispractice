package org.xavier.redis08bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
@AllArgsConstructor
public class ZeroOneMatrix {
    private Jedis client;
    private String bitmapKey;
    private int rowNumber;
    private int colNumber;

    public static String makeMatrixKey(String matrixName){
        return "matrix::" + matrixName;
    }

    public void set(int row, int column, int value) {
        int offset = row * this.rowNumber +column;
        boolean bvalue = value ==1? true: false;
        client.setbit(this.bitmapKey, Long.valueOf(offset), bvalue);
    }

    public int get(int row, int column){
        int offset = row * this.rowNumber +column;
        return client.getbit(this.bitmapKey, offset) == true? 1: 0;
    }

    public void show(){
        for(int i = 0; i<= rowNumber; i++) {
            for(int j=0; j<=colNumber; j++){
                System.out.print(this.get(i, j)+"\t");
            }
            System.out.println();
        }
    }

}
