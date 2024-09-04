(* Programming language concepts for software developers, 2010-08-28 *)

(* Evaluating simple expressions with variables *)

module Intro2

(* Association lists map object language variables to their values *)

let env = [("a", 3); ("c", 78); ("baf", 666); ("b", 111)];;

let emptyenv = []; (* the empty environment *)

let rec lookup env x =
    match env with 
    | []        -> failwith (x + " not found")
    | (y, v)::r -> if x=y then v else lookup r x;;

let cvalue = lookup env "c";;


(* Object language expressions with variables *)

type expr = 
  | CstI of int
  | Var of string
  | Prim of string * expr * expr
  | If of expr * expr * expr;;


let e1 = CstI 17;;

let e2 = Prim("+", CstI 3, Var "a");;

let e3 = Prim("+", Prim("*", Var "b", CstI 9), Var "a");;


let e4 = Prim("min", CstI 5, Var "a");;

let e5 = Prim("min", CstI 1, Var "a");;

let e6 = Prim("max", CstI 5, Var "a");;

let e7 = Prim("max", CstI 1, Var "a");;

let e8 = Prim("==", CstI 3, Var "a");;

let e9 = Prim("==", CstI 14, Var "a");;

let e10 = If(Var "a", CstI 11, CstI 22);;

(* Evaluation within an environment *)

(* let rec eval e (env : (string * int) list) : int =
    match e with
    | CstI i            -> i
    | Var x             -> lookup env x 
    | Prim("+", e1, e2) -> eval e1 env + eval e2 env
    | Prim("*", e1, e2) -> eval e1 env * eval e2 env
    | Prim("-", e1, e2) -> eval e1 env - eval e2 env
    | Prim("min", e1, e2) -> eval e1 env |> min <| eval e2 env
    | Prim("max", e1, e2) -> eval e1 env |> max <| eval e2 env
    | Prim("==", e1, e2) -> if eval e1 env = eval e2 env then 1 else 0
    | Prim _            -> failwith "unknown primitive";; *)

let rec eval e (env : (string * int) list) : int =
    match e with
    | CstI i            -> i
    | Var x             -> lookup env x 
    | Prim(ope, e1, e2) -> 
        let i1 = eval e1 env 
        let i2 = eval e2 env
        match ope with
        | "+" -> (+) i1 i2
        | "-" -> (-) i1 i2
        | "*" -> (*) i1 i2
        | "min" -> min i1 i2
        | "max" -> max i1 i2
        | "==" -> if i1 = i2 then 1 else 0
        | _ -> failwith "unknown primitive"
    | If (e1, e2, e3) -> 
        let i1 = eval e1 env
        let i2 = eval e2 env
        let i3 = eval e3 env
        if i1 <> 0 then i2 else i3;;



let e1v  = eval e1 env;;
let e2v1 = eval e2 env;;
let e2v2 = eval e2 [("a", 314)];;
let e3v  = eval e3 env;;
let e4v = eval e4 env;;
let e5v = eval e5 env;;
let e6v = eval e6 env;;
let e7v = eval e7 env;;
let e8v = eval e8 env;;
let e9v = eval e9 env;;
let e10v = eval e10 env;;


type aexpr = 
  | CstI of int
  | Var of string
  | Add of aexpr * aexpr
  | Mul of aexpr * aexpr
  | Sub of aexpr * aexpr;;
 
(* Sub(Var "v", Add(Var "w", Var "z")*)
(* Mul(CstI 2, Sub(Var "v", Add(Var "w", Var "z")))*)
(*Add (Var "x", Add(Var "y", Add(Var "z", Var "v")))*)

let rec fmt (aexpr : aexpr) : string = 
    let fmt_binop = fun op a1 a2 -> "(" + fmt a1 + op + fmt a2 + ")"
    match aexpr with
    | CstI i -> string i
    | Var v -> v
    | Add (a1, a2) -> fmt_binop "+" a1 a2
    | Mul (a1, a2) -> fmt_binop "*" a1 a2
    | Sub (a1, a2) -> fmt_binop "-" a1 a2;;

let rec simplify (aexp : aexpr) : aexpr =
    match aexp with
    | Add (a1, a2) -> 
        let e1 = simplify a1
        let e2 = simplify a2
        match e1, e2 with
        | CstI 0, a -> a
        | a, CstI 0 -> a
        | _ -> aexp

    | Sub (a1, a2) ->
        let e1 = simplify a1
        let e2 = simplify a2
        match e1, e2 with 
        | a, CstI 0 -> a
        | a, b when a = b -> CstI 0
        | _ -> aexp

    | Mul (a1, a2) ->
        let e1 = simplify a1
        let e2 = simplify a2
        match e1, e2 with
        | CstI 1, a -> a
        | a, CstI 1 -> a
        | CstI 0, _ -> CstI 0
        | _, CstI 0 -> CstI 0
        | _ -> aexp

    | _ -> aexp


let rec diff_rec aexp (Var x) = 
    match aexp with
    | CstI _ -> CstI 0
    | Var n when n = x -> CstI 1
    | Var _  -> CstI 0
    | Add (a1, a2) -> Add (diff_rec a1 (Var x), diff_rec a2 (Var x))
    | Sub (a1, a2) -> Sub (diff_rec a1 (Var x), diff_rec a2 (Var x))
    | Mul (a1, a2) -> Add (Mul (diff_rec a1 (Var x), a2), Mul ((diff_rec a2 (Var x), a1)));;

let diff aexp x = diff_rec aexp x |> simplify;;
    
