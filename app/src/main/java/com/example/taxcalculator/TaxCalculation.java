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
    public static double calculateCarTax(int region, int vehicle_type, double horsePower, int months, int release_date) {

        release_date-=2024;//fix

        double result = 0;
        int[] c_table; // tax coefficient
        double[] hp_table; // horsepower

        switch (region) {
            case 50://moscow oblast
                switch (vehicle_type) {
                    case 1://passenger vehicle
                        c_table = new int[]{10, 34, 49, 75, 150};
                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 2://motorcycle
                        c_table = new int[]{9, 16, 50};
                        hp_table = new double[]{20, 35, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 3://truck
                        if (release_date <= 5)
                            c_table = new int[]{20, 25, 33, 45, 58};
                        else
                            c_table = new int[]{25, 40, 50, 65, 85};

                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;

                }
                break;
            case 77://moscow
                switch (vehicle_type) {
                    case 1://passenger vehicle
                        c_table = new int[]{12, 25, 35, 45, 50, 65, 75, 150};
                        hp_table = new double[]{100, 125, 150, 175, 200, 225, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 2://motorcycle
                        c_table = new int[]{7, 15, 50};
                        hp_table = new double[]{20, 35, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 3://truck
                        c_table = new int[]{15, 26, 38, 55, 70};
                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;


                }

            case 78://saint petersburg
                switch (vehicle_type) {
                    case 1:
                        c_table = new int[]{24, 35, 50, 75, 150};
                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};

                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 2:
                        c_table = new int[]{10, 20, 30, 50};
                        hp_table = new double[]{20, 35, 90, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 3:
                        if (release_date <= 3)
                            c_table = new int[]{25, 40, 50, 55, 45};
                        else if (release_date <= 5)
                            c_table = new int[]{25, 40, 50, 55, 65};
                        else
                            c_table = new int[]{25, 40, 50, 55, 85};

                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;

                }

            case 92:
                switch (vehicle_type) {
                    case 1://passenger vehicle
                        c_table = new int[]{5, 7, 25, 75, 100};
                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};

                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 2://motorcycle
                        c_table = new int[]{2, 4, 10};
                        hp_table = new double[]{20, 35, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                    case 3://truck
                        c_table = new int[]{12, 20, 25, 30, 40};
                        hp_table = new double[]{100, 150, 200, 250, Double.MAX_VALUE};
                        for (int i = 0; i < hp_table.length; i++) {
                            if (horsePower <= hp_table[i]) {
                                return Math.round(horsePower * c_table[i] * (months / 12f));
                            }
                        }
                        break;
                }
        }
        return result; // ~ default
    }


}
