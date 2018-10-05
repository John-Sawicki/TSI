package com.example.android.tsi.utilities;

public class WireGauageCalc {
    private double doubleZgauge = 0.078, zeroGauage = 0.0983, twoGauge = 0.1563, fourGuge = 0.2485,
            sixGauge = 0.3951, eightGauge = 0.6282, tenGauge = 0.9989,twelveGauge = 1.588,
            fourteenGauge = 2.525, sixteenGgauge = 4.016,eighteenGauge = 6.385;
    public WireGauageCalc(){}
    public String calculateWireGauge(double distance , double resistanceMax, boolean imperial){
        if(imperial){
            if( (distance/1000)*eighteenGauge<=resistanceMax ) return "18AWG";
            if( (distance/1000)*sixteenGgauge<=resistanceMax ) return "16AWG";
            if( (distance/1000)*fourteenGauge<=resistanceMax ) return "14AWG";
            if( (distance/1000)*twelveGauge<=resistanceMax ) return "12AWG";
            if( (distance/1000)*tenGauge<=resistanceMax ) return "10AWG";
            if( (distance/1000)*eightGauge<=resistanceMax ) return "8AWG";
            if( (distance/1000)*sixGauge<=resistanceMax ) return "6AWG";
            if( (distance/1000)*fourGuge<=resistanceMax ) return "4AWG";
            if( (distance/1000)*twoGauge<=resistanceMax ) return "2AWG";
            if( (distance/1000)*zeroGauage<=resistanceMax ) return "0AWG";
            if( (distance*0.001)*doubleZgauge<=resistanceMax ) return "00AWG";
            return "Go really big!";
        }else{
            //if( (distance*0.00328)*eighteenGauge<=resistanceMax ) return "1.5mm²";
            //1.5mm, 4mm, 10mm, 25mm, 35mm
            return "metric";
        }

    }
}
