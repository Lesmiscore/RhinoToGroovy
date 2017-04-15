package com.nao20010128nao.RhinoToGroovy

import org.mozilla.javascript.Context
import org.mozilla.javascript.Function
import org.mozilla.javascript.Scriptable

/**
 * Created by nao on 2017/04/15.
 */
class GroovyObjectAdapter implements GroovyObject{
    private Scriptable obj
    private Context ctx

    GroovyObjectAdapter(Scriptable obj,Context ctx=Context.currentContext){
        this.@obj=obj
        this.@ctx=ctx
    }

    Object invokeMethod(String name, Object args){
        def got=obj.get(name,obj)
        if(got instanceof Function){
            return groovyish(got.call(ctx,obj.parentScope?:obj,obj,(Object[])args))
        }else{
            return null
        }
    }

    Object getProperty(String propertyName){
        groovyish(obj.get(propertyName,obj))
    }

    void setProperty(String propertyName, Object newValue){
        def scriptable
        if(newValue instanceof GroovyObjectAdapter){
            scriptable=newValue.@obj
        }else{
            scriptable=Context.javaToJS(newValue,obj)
        }
        obj.put(propertyName,obj,scriptable)
    }

    Object call(Object... args){
        if(obj instanceof Function){
            return groovyish(obj.call(ctx,obj.parentScope,obj,args))
        }else{
            return null
        }
    }

    Object getAt(int i){
        groovyish(obj.get(i,obj))
    }

    Object getAt(String i){
        groovyish(obj.get(i,obj))
    }

    Object toJavaObject(Class desiredType=Object){
        Context.jsToJava(obj,desiredType)
    }

    private Object groovyish(Object got){
        if(got instanceof Scriptable){
            return new GroovyObjectAdapter((Scriptable)got,ctx)
        }else{
            return got
        }
    }

    @Override
    String toString() {
        obj.toString()
    }
}
