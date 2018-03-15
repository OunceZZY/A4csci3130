package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 *
 * add another static method to check the input
 */

public class Contact implements Serializable {
    public String personalID;
    public String business_number;
    public String name;
    public String primary_business;
    public String address;
    public String province_territory;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    /**
     * a new constructor constructs a contact object that fits the need of assignment 3
     *
     * parameters as their names
     *
     * @param personalID
     * @param business_num
     * @param name
     * @param primary_business
     * @param address
     * @param province_territory
     */
    public Contact(String personalID, String business_num, String name, String primary_business, String address, String province_territory) {
        this.personalID = personalID;
        this.business_number = business_num;
        this.name = name;
        this.primary_business = primary_business;
        this.address = address;
        this.province_territory = province_territory;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("business_number", business_number);
        result.put("name", name);
        result.put("primary_business", primary_business);
        result.put("address", address);
        result.put("province_territory", province_territory);
        result.put("personalID", personalID);

        return result;
    }

    /**
     * To check the input is valid or not, rules follows the firerule.json
     *
     * @param name
     * @param b_num
     * @param prim_bus
     * @param addr
     * @param prov_terr
     * @return int[] contain a -1 on n position if there is error on position n
     */
    public static int[] inputValidation(String name, String b_num, String prim_bus, String addr, String prov_terr) {
        int[] ret = new int[5];
        name = name.trim();
        if (name.length() >= 49 || name.length() < 2) {
            ret[0] = -1;
        }
        for(int i = 0;ret[0]!= -1 &&  i<name.length();i++){
            if(!Character.isLetter(name.charAt(i)))
                ret[0] = -1;
        }

        // business number length validation
        if (b_num.length() != 9) {
            ret[1] = -1;
        }
        for (int i = 0; ret[1] != -1 && i < b_num.length(); i++) {
            if (!Character.isDigit(b_num.charAt(i)))
                ret[1] = -1;
        }

        // business type validation
        String[] business = {"Fisher", "Distributor", "Processor", "Fish Monger"};
        for (int i = 0; i < business.length; i++) {
            if (prim_bus.equals(business[i]))
                ret[2] = 1;
        }

        if (ret[2] != 1) ret[2] = -1;

        // address format validation
        if (addr.length() >= 50) ret[3] = -1;

        String[] provs = {"AB", "BC", "MB", "NB", "NL", "NS", "NT", "NU", "ON", "PE", "QC", "SK", "YT", ""};

        prov_terr.trim();
        for (int i = 0; i < provs.length; i++) {
            if (prov_terr.equals(provs[i])) {
                ret[4] = 1;
            }
        }
        if (ret[4] != 1) ret[4] = -1;

        return ret;
    }

}
