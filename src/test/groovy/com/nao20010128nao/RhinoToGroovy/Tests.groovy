package com.nao20010128nao.RhinoToGroovy

import junit.framework.TestCase
import org.junit.Test
import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable

/**
 * Created by nao on 2017/04/15.
 */
class Tests extends TestCase{
    @Test
    static void testExecution1(){
        def js='''
value={
    "test":"value",
    "array":[1,2,3,4,5,6],
    "func":function(){java.lang.System.out.println("Running");},
    "argsFunc":function(value){java.lang.System.out.println("value: "+value);},
    "argsFunc2":function(){
        var print="";
        for(a in arguments){
            print+=", "+a
        }
        return print.substring(2)
    }
}
'''
        def ctx=Context.enter()
        def scope=ctx.initStandardObjects()
        ctx.evaluateString(scope,js,"<script>",0,null)
        def adapter=new GroovyObjectAdapter(scope,ctx)
        def value=adapter.value

        println value.test
        value.func()
        value.argsFunc("This is a value")
        value.argsFunc(12345)
        value.argsFunc(null)
        println value.argsFunc2(1,2,"3",'4')
        println value.array[2]
        println value.array.length
    }
}
