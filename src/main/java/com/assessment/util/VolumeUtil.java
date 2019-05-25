package com.assessment.util;


import com.assessment.pojo.Box;
import com.assessment.pojo.Node;

import java.text.DecimalFormat;

public class VolumeUtil {

    /**
     * Rounds to the nearest 0.5
     * @param volume of the box
     * @return volumetric weight
     */
    public static double calculateVolumetricWeight(double volume) {
        double value =  Math.round(volume * 2) / 2.0;
        return value <= 0 ? 0.5 : value;
    }

    /**
     * Calculates volume of a box
     * @param box pojo
     * @return volume
     */
    public static double calculateVolume(Box box){
        return (box.getHeight() * box.getBreadth() * box.getLength()) / 5000;
    }

    public static double calculateShippingCost(Node node, double volume){
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(Math.sqrt(node.getDistance()) * volume));
    }



}
