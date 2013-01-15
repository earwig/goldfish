package edu.stuy.goldfish.rules;

import edu.stuy.goldfish.Grid;
import edu.stuy.goldfish.Patch;

public class Conway extends RuleSet {
    public static int states = 2;

    public static Grid run (Grid g) {
        //Thread[] threads = new Thread[g.getWidth()];
        Grid newGrid = new Grid(g.getWidth(), g.getHeight());
        for (int i = 0; i < g.getWidth(); i++) {
            //Conway.rowThread rt = new Conway.rowThread(g, newGrid, i);
            //Thread t = new Thread(rt);
            //rt.setThread(t);
            //threads[i] = t;
            //t.run();
            for (int j = 0; j < g.getHeight(); j++) {
                Patch[] neighbors = g.getPatch(i, j).get8Neighbors();
                int numAlive = 0;
                for (Patch neighbor : neighbors)
                    if (neighbor.getState() == 1) numAlive++;
                Patch p = g.getPatch(i,j).clone(newGrid);
                if (numAlive < 2) {
                    p.setState(0); //Dies by underpopulation
                }
                if (numAlive > 3) {
                    p.setState(0); //Dies by overpopulation
                }
                if (numAlive == 3)
                    p.setState(1); //Born with 3 neighbors.
                newGrid.setPatch(i,j,p);
            }
        }

        //boolean running = true;
        //while (running) {
            //running = false;
            //for (Thread t : threads) {
                //if (t.isAlive()) running = true;
            //}
        //}
        return newGrid;
    }


    //public static class rowThread implements Runnable { 
        //private Grid _original, _newGrid;
        //private int _rowNum;
        //private Thread _thread;

        //public rowThread (Grid orig, Grid newGrid, int rowNum) {
            //_original = orig;
            //_newGrid = newGrid;
            //_rowNum = rowNum;
        //}
        //public void setThread (Thread t) {
            //_thread = t;
        //}

        //public void run () {
            //for (int j = 0; j < _original.getHeight(); j++) {
                //Patch[] neighbors = _original.getPatch(_rowNum, j).get8Neighbors();
                //int numAlive = 0;
                //System.out.println(_original.getPatch(_rowNum, j).getX() + "," + _original.getPatch(_rowNum, j).getY());
                //for (Patch p : neighbors) {
                    //System.out.print(p.getState());
                    //if (p.getState() == 1) {
                        //numAlive++;
                    //}
                //}
                //System.out.println();
                //Patch p = _original.getPatch(_rowNum,j).clone();
                //if (numAlive < 2) {
                    //p.setState(0); //Dies by underpopulation
                //}
                //if (numAlive > 3) {
                    //p.setState(0); //Dies by overpopulation
                //}
                //if (numAlive == 3)
                    //p.setState(1); //Born with 3 neighbors.
                //_newGrid.setPatch(_rowNum,j,p);
            //}
            //try {
                //_thread.join();
            //} catch (Exception e) {
                //e.printStackTrace();
            //}
        //}
    //}
}
