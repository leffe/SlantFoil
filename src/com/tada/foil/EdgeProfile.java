package com.tada.foil;

import java.util.Iterator;
import java.util.List;


/**
 * Defines an profile moving in the x and y axis.
 * In the base form the profile is normalized and having
 * the values for restricted in [0.0,1.0]. The base form 
 * can be scaled arbitrarily.
 * The profile is not closed. Normally it starts in 0.0 and 
 * ends in 1.0.    
 * 
 *  The input data is a sequence of x,y pairs. x starting
 *  from 0.0 and ending at 1.0
 * 
 * @author Leif Sten
 *
 */
public class EdgeProfile extends BaseProfile {


    public EdgeProfile(List<DoublePair> profilePairs) {
//        Iterator<FloatPair> it = profilePairs.iterator();
//        while (it.hasNext()) {
//            FloatPair fp = it.next();
////            System.out.println("profile pairs: " + fp.getMa() + ", " + fp.getMb());
//        }
        setSpline(createSpline(profilePairs));
//        mSpline.show();
    }
    


	
	
}
