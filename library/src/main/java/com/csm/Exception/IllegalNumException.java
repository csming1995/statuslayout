package com.csm.Exception;

/**
 * Created by csm on 2017/7/7.
 */

public class IllegalNumException extends Exception {
    public IllegalNumException(){}

    public IllegalNumException(String gripe){
        super(gripe);
    }

    @Override
    public void printStackTrace(){
        super.printStackTrace();
        System.out.print("You can't choice 1,2,3 as your status key");
    }
}
