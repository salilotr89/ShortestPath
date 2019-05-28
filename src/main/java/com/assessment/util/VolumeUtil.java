package com.assessment.util;


import com.assessment.pojo.Box;
import com.assessment.pojo.Node;

import java.text.DecimalFormat;

public class VolumeUtil {

    //Javascript method analyzed to determine any missing conditions when calculating weight.
    /*public static double calculateVolumetricWeight(Box box, Node node)
    {
        double vol = box.getBreadth() * box.getLength() * box.getHeight();
        double volw =  vol / 5000;
        double b = volw - Math.round(volw);
        double c = 0;
        if( b <= .5 && 0 < b )
        {
            c=.5;
        }
        else //if( (.5 < b) && (b < 1 ) )
        {
            c=1;
        }
        if( b==0 ) c=0;
        double fweight = volw - b + c;
        //if( vol != 0 && ( Double.isNaN( fweight ) || fweight < 1 ) ) fweight = 1;

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(Math.sqrt(node.getDistance()) * fweight));
    }*/

    /**
     * Rounds to the nearest 0.5
     * @param volume of the box
     * @return volumetric weight
     */
    public static double roundVolume(double volume) {
        double value =  Math.round(volume * 2.0)  / 2.0;
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

    /**
     * Calculates the shipping cost based on the volumetric weight.
     * @param node fetches hard from the node
     * @param box used to calculated volume
     * @return value for assertion
     */
    public static double calculateShippingCost(Node node, Box box){
        double dmi = roundVolume((box.getHeight() * box.getBreadth() * box.getLength()) / 5000);
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(Math.sqrt(node.getDistance()) * dmi));
    }





}
