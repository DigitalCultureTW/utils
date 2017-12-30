/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tw.digitalculture.data.model;

import java.util.Comparator;

/**
 *
 * @author Jonathan Chang, Chun-yien <ccy@musicapoetica.org>
 */
public class RightsComparator implements Comparator<TWDC_Record> {

    @Override
    public int compare(TWDC_Record o1, TWDC_Record o2) {
        return o1.rights.ordinal() - o2.rights.ordinal();
    }

}
