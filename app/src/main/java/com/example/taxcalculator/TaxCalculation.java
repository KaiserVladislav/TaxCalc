package com.example.taxcalculator;

import java.util.HashMap;

public class TaxCalculation {
    public static double calculateNDFL(double input, String residency){
        if(input<=4000)
            return 0;
        if(residency.equals("Alien"))
            return Math.round((input-4000)*0.3);
        return input < 5_000_000 ? Math.round((input-4000)*0.13) : Math.round((input-5_000_000)*0.15 + (5_000_000-4000)*0.13);
    }
    public static double calculateNDS(double input, String type_of_good){
        if(type_of_good.equals("Other")){
            return Math.round(input*0.2);
        }else if(type_of_good.equals("Export")){
            return 0;
        }else{
            return Math.round(0.1*input);
        }
    }
    public static double calculateObligationsTax(double purchase_price, double sale_price, double amount_of_shares, String residency){
        return purchase_price>=sale_price? 0 : calculateNDFL(amount_of_shares*(sale_price-purchase_price), residency);
    }
    public static double calculateLotteryTax(double input, String type_of_lottery, String residency){
        if(input<=4000)
            return 0;
        if(residency.equals("Alien")){
            return calculateNDFL(input,residency);
        }

        if(type_of_lottery.equals("State")){
            calculateNDFL(input,residency);
        }else{
            return Math.round(0.35*(input-4000));
        }
        return 0;
    }
    public static double calculateCarTax(String region, double horsePower, int months){
        double result=0;
        if(region.equals("Moscow")){
            int[] c_table= {12,25,35,45,50,65,75,150};
            double[] hp_table = {100, 125, 150,175,200,225,250, Double.MAX_VALUE};
            result=0;
            for(int i =0;i<hp_table.length;i++){
                if (horsePower<=hp_table[i]){
                    return Math.round(horsePower*c_table[i]*(months/12f));
                }
            }
            return result;
        }
        else if(region.equals("Saint-Petersburg")){
            int[] c_table= {24,35,50,75,150};
            double[] hp_table = {100, 150, 200,250, Double.MAX_VALUE};
            result=0;
            for(int i =0;i<hp_table.length;i++){
                if (horsePower<=hp_table[i]){
                    return Math.round(horsePower*c_table[i]*(months/12f));
                }
            }
        }
        return result;
    }


}
