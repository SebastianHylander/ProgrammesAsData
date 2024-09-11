// File Intro/SimpleExpr.java
// Java representation of expressions as in lecture 1
// sestoft@itu.dk * 2010-08-29

import java.util.Map;
import java.util.HashMap;


// This file was handed to us as part of the excercise. We were supposed to implement the rest of the Expr classes. 

abstract class Expr { 
    abstract public int eval(Map<String,Integer> env);
    abstract public String fmt();
    abstract public String fmt2(Map<String,Integer> env);

    public String toString(){
        return fmt(); // The excercise specifies that we should use toString instead of fmt ;)
    } 

    public Expr simplify(){
        return this;
    }
}

class CstI extends Expr { 
    protected final int i;

    public CstI(int i) { 
        this.i = i; 
    }

    public int eval(Map<String,Integer> env) {
        return i;
    } 

    public String fmt() {
        return ""+i;
    }

    public String fmt2(Map<String,Integer> env) {
        return ""+i;
    }
}

class Var extends Expr { 
    protected final String name;

    public Var(String name) { 
        this.name = name; 
    }

    public int eval(Map<String,Integer> env) {
        return env.get(name);
    } 

    public String fmt() {
        return name;
    } 

    public String fmt2(Map<String,Integer> env) {
        return ""+env.get(name);
    } 
}

abstract class Binop extends Expr {
    protected final Expr e1, e2;

    public Binop(Expr e1, Expr e2) {
      this.e1 = e1; this.e2 = e2;
    }

    abstract public int eval(Map<String,Integer> env);
    abstract public String fmt();
    abstract public String fmt2(Map<String,Integer> env);
}

class Add extends Binop {
    public Add(Expr e1, Expr e2) {
        super(e1, e2);
    }

    public int eval(Map<String,Integer> env) {
        return e1.eval(env) + e2.eval(env);
    }

    public String fmt() {
        return "(" + e1.fmt() + " + " + e2.fmt() + ")";
    }

    public String fmt2(Map<String,Integer> env) {
        return "(" + e1.fmt2(env) + " + " + e2.fmt2(env) + ")";
    }

    @Override
    public Expr simplify(){
        Expr e1s = e1.simplify();
        Expr e2s = e2.simplify();
        if (e1s instanceof CstI){
            CstI c1 = (CstI) e1s;
            if (c1.i == 0){
                return e2s;
            }
        }
        if (e2s instanceof CstI){
            CstI c2 = (CstI) e2s;
            if (c2.i == 0){
                return e1s;
            }
        }
        return super.simplify();
    }
}

class Sub extends Binop {
    public Sub(Expr e1, Expr e2) {
        super(e1, e2);
    }

    public int eval(Map<String,Integer> env) {
        return e1.eval(env) - e2.eval(env);
    }

    public String fmt() {
        return "(" + e1.fmt() + " - " + e2.fmt() + ")";
    }

    public String fmt2(Map<String,Integer> env) {
        return "(" + e1.fmt2(env) + " - " + e2.fmt2(env) + ")";
    }

    @Override
    public Expr simplify(){
        Expr e1s = e1.simplify();
        Expr e2s = e2.simplify();

        if (e2s instanceof CstI){
            CstI c2 = (CstI) e2s;
            if (c2.i == 0){
                return e1s;
            }
            else if (e1s instanceof CstI){
                CstI c1 = (CstI) e1s;
                if (c1.i == c2.i){
                    return new CstI(0);
                }
            }
        }
        return super.simplify();
    }
}

class Mul extends Binop {
    public Mul(Expr e1, Expr e2) {
        super(e1, e2);
    }

    public int eval(Map<String,Integer> env) {
        return e1.eval(env) * e2.eval(env);
    }

    public String fmt() {
        return "(" + e1.fmt() + " * " + e2.fmt() + ")";
    }

    public String fmt2(Map<String,Integer> env) {
        return "(" + e1.fmt2(env) + " * " + e2.fmt2(env) + ")";
    }

    @Override
    public Expr simplify(){
        Expr e1s = e1.simplify();
        Expr e2s = e2.simplify();
        if (e1s instanceof CstI){
            CstI c1 = (CstI) e1s;
            if (c1.i == 0){
                return new CstI(0);
            }
            else if (c1.i == 1){
                return e2s;
            }
        }
        if (e2s instanceof CstI){
            CstI c2 = (CstI) e2s;
            if (c2.i == 0){
                return new CstI(0);
            }
            else if (c2.i == 1){
                return e1s;
            }
        }
        return super.simplify();
    }
}


class Prim extends Expr { 
    protected final String oper;
    protected final Expr e1, e2;

    public Prim(String oper, Expr e1, Expr e2) { 
        this.oper = oper; this.e1 = e1; this.e2 = e2;
    }

    public int eval(Map<String,Integer> env) {
        if (oper.equals("+"))
        return e1.eval(env) + e2.eval(env);
        else if (oper.equals("*"))
        return e1.eval(env) * e2.eval(env);
        else if (oper.equals("-"))
        return e1.eval(env) - e2.eval(env);
        else
        throw new RuntimeException("unknown primitive");
    } 

    public String fmt() {
        return "(" + e1.fmt() + oper + e2.fmt() + ")";
    } 

    public String fmt2(Map<String,Integer> env) {
        return "(" + e1.fmt2(env) + oper + e2.fmt2(env) + ")";
    } 

}

public class SimpleExpr {
    public static void main(String[] args) {
        Expr e1 = new CstI(17);
        Expr e2 = new Prim("+", new CstI(3), new Var("a"));
        Expr e3 = new Prim("+", new Prim("*", new Var("b"), new CstI(9)), 
                        new Var("a"));
        Map<String,Integer> env0 = new HashMap<String,Integer>();
        env0.put("a", 3);
        env0.put("c", 78);
        env0.put("baf", 666);
        env0.put("b", 111);

        System.out.println("Env: " + env0.toString());

        System.out.println(e1.fmt() + " = " + e1.fmt2(env0) + " = " + e1.eval(env0));
        System.out.println(e2.fmt() + " = " + e2.fmt2(env0) + " = " + e2.eval(env0));
        System.out.println(e3.fmt() + " = " + e3.fmt2(env0) + " = " + e3.eval(env0));

        
        Expr e4 = new Add (new CstI(3), new Mul(new CstI(5), new Var("x")));
        Expr e5 = new Mul(new Mul(new Add(new CstI(7), new CstI(4)), new Var ("y")), new Var("z"));
        Expr e6 = new Sub(new Mul(new CstI(3), new Var("x")), new Add(new CstI(7), new Var("y")));

        System.out.println(e4.toString());
        System.out.println(e5.toString());
        System.out.println(e6.toString());

    }
}
